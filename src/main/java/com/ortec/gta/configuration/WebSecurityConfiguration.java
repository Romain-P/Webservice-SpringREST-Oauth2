package com.ortec.gta.configuration;

import com.ortec.gta.service.authentication.ExternalAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import java.util.Collections;

/**
 * @Author: romain.pillot
 * @Date: 25/07/2017
 */

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private ExternalAuthenticationProvider authenticationProvider;

    /**
     * @return a custom AuthenticationProvider implementation, required to
     * perform a connection by username and password.
     * The default implementation of spring (UserDetails) provides an username only.
     */
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return new ProviderManager(Collections.singletonList(authenticationProvider));
    }
}
