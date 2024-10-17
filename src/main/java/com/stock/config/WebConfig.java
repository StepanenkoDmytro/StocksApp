package com.stock.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("Content-Type", "Authorization")
                .allowCredentials(false)
                .maxAge(3600);
    }

    @Bean
    public ClassLoaderTemplateResolver templateResolver () {
        ClassLoaderTemplateResolver resolver  =  new  ClassLoaderTemplateResolver ();

        resolver.setPrefix( "templates/" );
        resolver.setCacheable( false );
        resolver.setSuffix( ".html" );
        resolver.setTemplateMode( "HTML" );
        resolver.setCharacterEncoding( "UTF-8" );

        return resolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine () {
        SpringTemplateEngine  engine  =  new  SpringTemplateEngine ();
        engine.setTemplateResolver(templateResolver());

        return engine;
    }
}
