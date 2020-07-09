package com.niu.springboot.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
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

    final RabbitTemplate.ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback() {
        @Override
        public void confirm(CorrelationData correlationData, boolean ack, String cause) {
            System.err.println("--------------------- ConfirmCallback ------------------------");

            System.out.println("correlationData: " + correlationData);
            System.out.println("ack: " + ack);
            System.out.println("cause: " + cause);

            if (!ack) {
                System.out.println("异常处理");
            }
        }
    };

    final RabbitTemplate.ReturnCallback returnCallback = new RabbitTemplate.ReturnCallback() {
        @Override
        public void returnedMessage(org.springframework.amqp.core.Message message, int replyCode, String replyText, String exchange, String routingKey) {
            System.err.println("--------------------- ReturnCallback ------------------------");
            System.out.println("message: " + message);
            System.out.println("replyCode: " + replyCode);
            System.out.println("replyText: " + replyText);
            System.out.println("exchange: " + exchange);
            System.out.println("routingKey: " + routingKey);
        }
    };

    public void send(Object message, Map<String, Object> properties) {

        MessageHeaders msgHeader = new MessageHeaders(properties);
        Message msg = MessageBuilder.createMessage(message, msgHeader);
        rabbitTemplate.setConfirmCallback(confirmCallback);
        rabbitTemplate.setReturnCallback(returnCallback);
        CorrelationData cd = new CorrelationData();
        // id 全局唯一
        cd.setId("123");
//        rabbitTemplate.convertAndSend("exchange-1", "springboot.hello", msg, cd);
        rabbitTemplate.convertAndSend("exchange-1", "spring.hello", msg, cd);
    }
}
