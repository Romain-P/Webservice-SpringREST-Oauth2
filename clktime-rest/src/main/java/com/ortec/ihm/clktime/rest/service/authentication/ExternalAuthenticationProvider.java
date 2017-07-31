package com.ortec.ihm.clktime.rest.service.authentication;

import com.ortec.ihm.clktime.rest.model.dto.GlobalUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @Author: romain.pillot
 * @Date: 25/07/2017
 */
@Component
public class ExternalAuthenticationProvider implements AuthenticationProvider, Serializable {

    @Autowired
    AuthenticationService authenticationService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();

        GlobalUser user = authenticationService
                .loadByConnection(name, password)
                .orElseThrow(() ->
                        new BadCredentialsException(String.format("Bad user/pass for %s", name)));

        return new UsernamePasswordAuthenticationToken(user, null, user.getRoles());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
