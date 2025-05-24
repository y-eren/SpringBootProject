package com.imageprocessing.imageprocessproject.messaging;

import com.imageprocessing.imageprocessproject.dto.TransformRequestDTO;
import com.imageprocessing.imageprocessproject.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class ImageConsumer {

    private final ImageService imageService;

    @RabbitListener(queues = "imageProcessingQueue")
    public void process(TransformRequestDTO transformRequestDTO) throws IOException {
        imageService.transformImage(transformRequestDTO.getId(),transformRequestDTO, transformRequestDTO.getUsername());
    }
}
