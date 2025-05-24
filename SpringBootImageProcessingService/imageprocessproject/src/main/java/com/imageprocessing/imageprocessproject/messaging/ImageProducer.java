package com.imageprocessing.imageprocessproject.messaging;

import com.imageprocessing.imageprocessproject.dto.TransformRequestDTO;
import com.imageprocessing.imageprocessproject.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ImageProducer {

    private final RabbitTemplate rabbitTemplate;

    public void sendMessage(TransformRequestDTO transformRequestDTO) {
        rabbitTemplate.convertAndSend("imageProcessingQueue", transformRequestDTO);
    }

}
