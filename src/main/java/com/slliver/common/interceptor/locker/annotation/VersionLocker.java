package com.slliver.common.interceptor.locker.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description: 用一句话具体描述类的功能
 * @author: slliver
 * @date: 2018/1/31 17:48
 * @version: 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface VersionLocker {
    // 默认开启乐观锁，如果不想开启乐观锁，在方法前加入 @VersionLocker(false)
    boolean value() default true;
}
