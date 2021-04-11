package com.abdullah.home.automation.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/webjars/**", "/img/**", "/css/**", "/js/**")
                .addResourceLocations(
                "classpath:/META-INF/resources/webjars/", "classpath:/static/img/", "classpath:/static/css/",
                "classpath:/static/js/");
    }
    //@Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        //http://localhost:8080/shared/server.png
//        registry
//                .addResourceHandler("/webjars/**","/shared/**")
//                .addResourceLocations("classpath:/META-INF/resources/webjars/", "file:/Users/cmabdullahkhan/shared/");
//    }

//

}