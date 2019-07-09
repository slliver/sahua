package com.slliver.common.mq;

import org.springframework.amqp.core.AmqpTemplate;

/**
 * @Description: 消息产生着
 * @author: slliver
 * @date: 2018/11/27 14:53
 * @version: 1.0
 */
public class MessageSender {

    private AmqpTemplate amqpTemplate;

    private String routingKey;


    public AmqpTemplate getAmqpTemplate() {
        return amqpTemplate;
    }

    public void setAmqpTemplate(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    public String getRoutingKey() {
        return routingKey;
    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }

    public void sendDataToQueue(Object obj) {
        amqpTemplate.convertAndSend(this.routingKey, obj);
    }
}
