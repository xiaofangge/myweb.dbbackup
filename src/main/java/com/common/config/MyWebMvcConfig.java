package com.common.config;

import com.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;


@Configuration
public class MyWebMvcConfig implements WebMvcConfigurer {

    private final String[] openPaths = {"/captcha", "/user/login", "/user/register", "/", "",
    "/login", "/register", "/static/**"};

    @Qualifier("loginInterceptor")
    @Resource
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(openPaths);
    }

    /**
     * 配置转义字符,解决当请求路径中特殊字符，高版本tomcat解析失败的问题
     */
    @Bean
    public ServletWebServerFactory webServerFactory() {
        TomcatServletWebServerFactory fa = new TomcatServletWebServerFactory();
        fa.addConnectorCustomizers(connector -> {
            connector.setProperty("relaxedQueryChars", "\\");
            connector.setProperty("rejectIllegalHeader", "false");
        });
        return fa;
    }
}
