package com.imageprocessing.imageprocessproject.dto;

import com.imageprocessing.imageprocessproject.model.Image;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ImageResponseDTO {

    private Long id;
    private String fileName;
    private String url;
    private String contentType;
    private String size;

    public ImageResponseDTO(Image image) {
        this.id = image.getId();
        this.fileName = image.getFileName();
        this.url = image.getUrl();
        this.contentType = image.getContentType();
        this.size = image.getSize();

    }

    // private Image image;
}
