package com.imageprocessing.imageprocessproject.config;



import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;


@Configuration
public class RabbitConfig {
    @Bean
    public Queue imageProcessingQueue() {
        return new Queue("imageProcessingQueue", false);
    }

    // rabbit icin jsona donusturuyor
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }




}
