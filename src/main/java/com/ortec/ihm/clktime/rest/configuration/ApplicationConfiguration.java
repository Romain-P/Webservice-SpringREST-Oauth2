package com.ortec.ihm.clktime.rest.configuration;

import com.ortec.ihm.clktime.rest.configuration.annotation.Tokened;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * @Author: romain.pillot
 * @Date: 25/07/2017
 */

/**
 * Main configuration that scan the whole project annotated with
 * spring annotations. It enable web-mvc and load the property files.
 */
@Configuration
@EnableWebMvc
@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = "com.ortec.ihm.clktime.rest")
public class ApplicationConfiguration extends WebMvcConfigurerAdapter{

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    /**
     * Custom Implementation of HandlerMethodArgumentResolver to retrieve
     * a GlobalUser on any method parameters annotated with @Tokened,
     * a method called by an user request (might be used into controllers).
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new HandlerMethodArgumentResolver() {

            public boolean supportsParameter(MethodParameter parameter) {
                return findMethodAnnotation(Tokened.class, parameter) != null;
            }

            public Object resolveArgument(
                    MethodParameter parameter,
                    ModelAndViewContainer mavContainer,
                    NativeWebRequest webRequest,
                    WebDataBinderFactory binderFactory) throws Exception
            {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                Object principal = authentication.getPrincipal();

                if(principal != null && !parameter.getParameterType().isAssignableFrom(principal.getClass()))
                    throw new ClassCastException(principal + " is not assignable to " + parameter.getParameterType());

                return principal;
            }

            <T extends Annotation> T findMethodAnnotation(Class<T> annotationClass, MethodParameter parameter) {
                T annotation = parameter.getParameterAnnotation(annotationClass);

                if(annotation != null)
                    return annotation;

                Annotation[] annotationsToSearch = parameter.getParameterAnnotations();
                for(Annotation toSearch : annotationsToSearch) {
                    annotation = AnnotationUtils.findAnnotation(toSearch.annotationType(), annotationClass);

                    if(annotation != null)
                        return annotation;
                }
                return null;
            }
        });
    }

}
