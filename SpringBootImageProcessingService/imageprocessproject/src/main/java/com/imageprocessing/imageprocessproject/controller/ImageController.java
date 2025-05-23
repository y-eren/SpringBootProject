package com.imageprocessing.imageprocessproject.controller;


import com.imageprocessing.imageprocessproject.dto.ImageResponseDTO;
import com.imageprocessing.imageprocessproject.dto.TransformRequestDTO;
import com.imageprocessing.imageprocessproject.model.Image;
import com.imageprocessing.imageprocessproject.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @PostMapping("/images")
    public ResponseEntity<Image> uploadImage (@RequestParam("file") MultipartFile file, Authentication authentication) throws IOException {

      Image image =  imageService.uploadImage(file,authentication.getName());
      return ResponseEntity.ok(image);
    }

    @GetMapping("/images/{id}")
    public ResponseEntity<ImageResponseDTO> getImageById(@PathVariable("id") Long id) {

    Image image = imageService.findImage(id);
       try {
           return ResponseEntity.status(HttpStatus.OK).body(new ImageResponseDTO(image));
       }
       catch (Exception e) {
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ImageResponseDTO(null));
       }
    }

    @RequestMapping("/images/{id}/transform")
    public ResponseEntity<ImageResponseDTO> createImage(@PathVariable Long id, @RequestBody TransformRequestDTO requestDTO, Authentication authentication) throws IOException {
            Image image = imageService.transformImage(id, requestDTO, authentication.getName());
        try {
            return ResponseEntity.status(HttpStatus.OK).body(new ImageResponseDTO(image));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ImageResponseDTO(null));
        }
    }

    @GetMapping("/images")
    public ResponseEntity<List<ImageResponseDTO>> getAllImages(@RequestParam int page, @RequestParam int limit) {
        Page<Image> images = imageService.findAllImages(page,limit);
        Page<ImageResponseDTO> dtoPage = images.map(ImageResponseDTO::new);
        return ResponseEntity.status(HttpStatus.OK).body(dtoPage.getContent());
    }



}
