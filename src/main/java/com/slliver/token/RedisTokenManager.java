package com.slliver.token;

import com.slliver.common.Constant;
import com.slliver.common.domain.UserToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 用一句话具体描述类的功能
 * @author: slliver
 * @date: 2018/3/12 10:14
 * @version: 1.0
 */
@Component
public class RedisTokenManager implements TokenManager {


    private RedisTemplate redisTemplate;

    @Autowired
    public void setRedis(RedisTemplate redis) {
        this.redisTemplate = redis;
        //泛型设置成Long后必须更改对应的序列化方案
        redis.setKeySerializer(new JdkSerializationRedisSerializer());
    }

    public UserToken createToken(String userPkid) {
        //使用uuid作为源token
        String token = UUID.randomUUID().toString().replace("-", "");
        UserToken model = new UserToken(userPkid, token);
        //存储到redis并设置过期时间
        redisTemplate.boundValueOps(userPkid).set(token, Constant.TOKEN_EXPIRES_HOUR, TimeUnit.HOURS);
        return model;
    }

    public UserToken getToken(String authentication) {
        if (authentication == null || authentication.length() == 0) {
            return null;
        }
        String[] param = authentication.split("_");
        if (param.length != 2) {
            return null;
        }
        //使用userId和源token简单拼接成的token，可以增加加密措施
        String userPkid = param[0];
        String token = param[1];
        return new UserToken(userPkid, token);
    }

    public boolean checkToken(UserToken model) {
        if (model == null) {
            return false;
        }
        String token = redisTemplate.boundValueOps(model.getUserPkid()).get().toString();
        if (token == null || !token.equals(model.getToken())) {
            return false;
        }
        //如果验证成功，说明此用户进行了一次有效操作，延长token的过期时间
        redisTemplate.boundValueOps(model.getUserPkid()).expire(Constant.TOKEN_EXPIRES_HOUR, TimeUnit.HOURS);
        return true;
    }

    public void deleteToken(String userId) {
        redisTemplate.delete(userId);
    }
}
