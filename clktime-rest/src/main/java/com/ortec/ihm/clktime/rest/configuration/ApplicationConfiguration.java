package com.ortec.ihm.clktime.rest.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @Author: romain.pillot
 * @Date: 25/07/2017
 */

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.ortec.ihm.clktime.rest")
public class ApplicationConfiguration extends WebMvcConfigurerAdapter{
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
}
