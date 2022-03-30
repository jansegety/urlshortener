package jansegety.urlshortener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jansegety.urlshortener.interceptor.AuthLoginInterceptor;
import jansegety.urlshortener.service.UserService;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        AuthLoginInterceptor authloginIntercepter = getAuthloginIntercepter();
        registry.addInterceptor(authloginIntercepter)
                .excludePathPatterns(authloginIntercepter.loginInEssential);
    }
   
   @Autowired
   private UserService userService;
    
    @Bean
    public AuthLoginInterceptor getAuthloginIntercepter(){
     return new AuthLoginInterceptor(userService);
    }
    
}