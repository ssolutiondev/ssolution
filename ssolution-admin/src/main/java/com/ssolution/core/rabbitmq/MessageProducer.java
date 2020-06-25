package com.ssolution.core.rabbitmq;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssolution.admin.system.util.DateUtil;
import com.ssolution.core.exception.ServiceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.UUID;

@Component
public class MessageProducer {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private final static String HOST_NAME = "ADMIN-PORTAL";

    public void produceMessageQueue(String exchange, String routingKey, Map<String,Object> request){
        try {
            String requestJson = new ObjectMapper().writeValueAsString(request);

            MessageProperties properties = new MessageProperties();
            properties.getHeaders().put("producer", HOST_NAME);
            properties.getHeaders().put("uuid", UUID.randomUUID());
            properties.getHeaders().put("timestamp", DateUtil.getDateStringYYYYMMDDHH24MISS(0));
            Message message = new Message(requestJson.getBytes("UTF-8"), properties);
            rabbitTemplate.convertAndSend(exchange,routingKey,message);
            logger.info("Push message has been published. {}" , requestJson);
        } catch (JsonProcessingException e) {
            logger.warn("error occured when make json string. {} ", request.toString());
            new ServiceException("LAB.M15.LAB00279");
        } catch (UnsupportedEncodingException e) {
            logger.warn("error occured when make json string encoding. {} ", request.toString());
            new ServiceException("LAB.M15.LAB00279");
        } catch (Exception e){
            logger.warn("error occured when push message publish. {} ", request.toString());
            new ServiceException("LAB.M15.LAB00279");
        }
    }
}