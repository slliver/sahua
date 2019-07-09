package com.slliver.common.mq;

import org.springframework.amqp.support.converter.DefaultClassMapper;

/**
 * @Description: 用一句话具体描述类的功能
 * @author: slliver
 * @date: 2018/11/28 16:10
 * @version: 1.0
 */
public class RabbitMQFastJsonClassMapper extends DefaultClassMapper {
    /**
     * 构造函数初始化信任所有pakcage
     */
    public RabbitMQFastJsonClassMapper() {
        super();
//        setTrustedPackages("*");
    }
}
