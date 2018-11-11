package com.kaka.house.web.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConf implements WebMvcConfigurer  {

    @Autowired
    AuthInterceptor authInterceptor;
    @Autowired
    AuthActionInterceptor authActionInterceptor;
    //定制拦截器顺序
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor).excludePathPatterns("/static").addPathPatterns("/**");
        registry.addInterceptor(authActionInterceptor).addPathPatterns("/accounts/profile");

    }
}
