package com.slliver.common.mq;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.AbstractMessageConverter;
import org.springframework.amqp.support.converter.ClassMapper;
import org.springframework.amqp.support.converter.MessageConversionException;

import java.io.UnsupportedEncodingException;

/**
 * @Description: Rabbitmq中自定义消息转换器
 * @author: slliver
 * @date: 2018/11/27 16:26
 * @version: 1.0
 */
public class RabbitMQFastJsonMessageConverter extends AbstractMessageConverter {

    /**
     * 日志对象实例
     */
    private Logger logger = LoggerFactory.getLogger(RabbitMQFastJsonMessageConverter.class);

    /**
     * 消息类型映射对象
     */
    private static ClassMapper classMapper = new RabbitMQFastJsonClassMapper();
    /**
     * 默认字符集
     */
    public final static String DEFAULT_CHARSET = "UTF-8";


    public RabbitMQFastJsonMessageConverter() {
        super();
    }

    @Override
    protected Message createMessage(Object obj, MessageProperties properties) {
        byte[] bytes = null;
        try {
            String jsonString = JSON.toJSONString(obj);
            bytes = jsonString.getBytes(DEFAULT_CHARSET);
        } catch (UnsupportedEncodingException e) {
            throw new MessageConversionException("Failed to convert Message content", e);
        }

        properties.setContentType(MessageProperties.CONTENT_TYPE_JSON);
        properties.setContentEncoding(DEFAULT_CHARSET);
        if (bytes != null) {
            properties.setContentLength(bytes.length);
        }

        classMapper.fromClass(obj.getClass(), properties);
        return new Message(bytes, properties);
    }

    @Override
    public Object fromMessage(Message message) throws MessageConversionException {
        Object content = null;
        // 获取消息的配置信息
        MessageProperties properties = message.getMessageProperties();
        if (properties == null) {
            return content;
        }

        // 消息类型
        String contentType = properties.getContentType();
        // 消息体
        byte[] body = message.getBody();
        if (StringUtils.isBlank(contentType)) {
            logger.warn("FastJsonMessageConverter Could not convert incoming message with content-type [" + contentType + "]");
        } else {
            String encoding = properties.getContentEncoding();
            if (StringUtils.isBlank(encoding)) {
                encoding = DEFAULT_CHARSET;
            }
            // 转成成对应的class类型
            Class<?> targetClass = classMapper.toClass(properties);
            try {
                content = JSON.parseObject(new String(body, encoding), targetClass);
            } catch (UnsupportedEncodingException e) {
                throw new MessageConversionException("Failed to convert Message content", e);
            }
        }

        if (content == null) {
            content = body;
        }

        return content;
    }
}
