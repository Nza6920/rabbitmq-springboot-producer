package com.niu.springboot;

import com.niu.springboot.entity.Order;
import com.niu.springboot.producer.RabbitSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RabbitmqSpringbootProducerApplicationTests {

    @Autowired
    private RabbitSender rabbitSender;

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Test
    public void contextLoads() {
    }


    @Test
    public void testSender1() throws InterruptedException {
        Map<String, Object> properties = new HashMap<>();
        properties.put("number", "12345");
        properties.put("send_time", simpleDateFormat.format(new Date()));

        rabbitSender.send("Hello RabbitMQ For Spring Boot", properties);
        rabbitSender.send("Hello RabbitMQ For Spring Boot", properties);

        Thread.sleep(2000);
    }

    @Test
    public void testSender2() throws InterruptedException {

        Order order = new Order("1", "nza");

        rabbitSender.sendOrder(order);

        Thread.sleep(3000);
    }
}
