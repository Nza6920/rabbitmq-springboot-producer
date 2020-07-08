package com.niu.springboot.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Description: TODO
 * @Author nza
 * @Date 2020/7/8
 **/
@Component
public class RabbitSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(Object message, Map<String, Object> properties) {

        MessageHeaders msgHeader = new MessageHeaders(properties);
        Message msg = MessageBuilder.createMessage(message, msgHeader);
        rabbitTemplate.convertAndSend("exchange-1", "spring_boot_hello", msg);
    }
}
