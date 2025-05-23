package com.imageprocessing.imageprocessproject.service;

import com.imageprocessing.imageprocessproject.dto.TransformRequestDTO;
import com.imageprocessing.imageprocessproject.helper.SepiaFilterOp;
import com.imageprocessing.imageprocessproject.model.Image;
import com.imageprocessing.imageprocessproject.model.User;
import com.imageprocessing.imageprocessproject.repository.ImageRepository;
import com.imageprocessing.imageprocessproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.imgscalr.Scalr;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;
    private final UserRepository userRepository;

    public Image uploadImage(MultipartFile file, String username) throws IOException {
        Image image = new Image();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Kullanici bulunamadi"));

        String originalFilename = file.getOriginalFilename();
        String storedFileName = UUID.randomUUID() + "_" + originalFilename;

        Path imagePath = Paths.get("uploads").resolve(storedFileName);

        Files.createDirectories(imagePath.getParent());
        Files.write(imagePath, file.getBytes());

        image.setUrl(imagePath.toString());
        image.setContentType(file.getContentType());
        image.setUser(user);
        image.setUploadDate(LocalDateTime.now());
        image.setSize(String.valueOf(file.getSize()));
        image.setFileName(storedFileName);
        return imageRepository.save(image);

    }

    public Image findImage(Long id) {
        return imageRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public Image transformImage(Long id, TransformRequestDTO transformRequestDTO, String username) throws IOException {
        Image image = findImage(id);
        Path originalPath = Paths.get("uploads", image.getFileName());

        BufferedImage bufferedImage;

        try {
            bufferedImage = ImageIO.read(originalPath.toFile());
        }
        catch (IOException e) {
            throw new RuntimeException("Image okunurken hata olustu", e);
        }

        BufferedImage result = bufferedImage;

        // Resize islemleri
        if (transformRequestDTO.getTransformations().getResize() != null) {
            var resize = transformRequestDTO.getTransformations().getResize();
            result = Scalr.resize(result, Scalr.Method.QUALITY, resize.getWidth(), resize.getHeight());

        }

        // rotate islemleri
        if(transformRequestDTO.getTransformations().getRotate() != null) {
            var rotate = transformRequestDTO.getTransformations().getRotate();
            result = Scalr.rotate(result, Scalr.Rotation.valueOf("ROTATE_" + transformRequestDTO.getTransformations().getRotate()));
        }
    // crop islemleri
        if (transformRequestDTO.getTransformations().getCrop() != null) {
            var crop = transformRequestDTO.getTransformations().getCrop();
            result = Scalr.crop(result, crop.getX(), crop.getY(), crop.getWidth(), crop.getHeight());
        }
        // filter islemleri
        if (transformRequestDTO.getTransformations().getFilters() != null) {
            var filters = transformRequestDTO.getTransformations().getFilters();
            if (filters.isGrayscale()) {
                result = Scalr.apply(result, Scalr.OP_GRAYSCALE);
            }


            if (filters.isSepia()) {
                result = Scalr.apply(result, new SepiaFilterOp());
            }

        }

            String format = Optional.ofNullable(transformRequestDTO.getTransformations().getFormat()).orElse("jpeg");
            String transformedName = UUID.randomUUID() + "." + format;
            Path transformedPath = Paths.get("uploads", transformedName);

            try {
                ImageIO.write(result, format,transformedPath.toFile());
            }
            catch (IOException e) {
                throw new RuntimeException("Image transform edilirken bir hata olustu", e);
            }

            User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Kullanici bulunamadi"));
            Image newImage = new Image();
            newImage.setUrl(transformedPath.toString());
            newImage.setContentType(format);
            newImage.setFileName(transformedName);
            newImage.setUploadDate(LocalDateTime.now());
            newImage.setSize(String.valueOf(Files.size(transformedPath)));
            newImage.setUser(user);

            return imageRepository.save(newImage);



    }

    public Page<Image> findAllImages(int page, int limit) {
        Pageable pageable = PageRequest.of(page,limit);
        return imageRepository.findAll(pageable);
    }

}
