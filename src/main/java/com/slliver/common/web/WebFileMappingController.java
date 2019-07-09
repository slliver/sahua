package com.slliver.common.web;

import com.slliver.common.utils.ResourceUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.regex.Pattern;

/**
 * @Description: 用一句话具体描述类的功能
 * @author: slliver
 * @date: 2018/3/16 17:13
 * @version: 1.0
 */
@Configuration
@EnableWebMvc
//@PropertySource("/WEB-INF/spring/application.properties")
public class WebFileMappingController extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String uploadPath = "/home/user/upload/";
        String osName = System.getProperty("os.name");
        if (Pattern.matches("Linux.*", osName)) {
            uploadPath = "/home/user/upload/";
        }
        if (Pattern.matches("Windows.*", osName)) {
            uploadPath = ResourceUtil.SERVER_PATH;
        }

//        registry.addResourceHandler("/resource/**").addResourceLocations("file:" + uploadPath);
        registry.addResourceHandler("/resource/**").addResourceLocations("file:" + "d:/robin/uplolad/");

        super.addResourceHandlers(registry);
    }
}
