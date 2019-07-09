package com.slliver.common.interceptor.token;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Token {

    /**
     * Token(save=true)：将随机生成的Token放入Session中
     * @return
     */
    boolean save() default false;

    /**
     * Token(save=true)：将随机生成的Token从Session中移出
     * @return
     */
    boolean remove() default false;
}
