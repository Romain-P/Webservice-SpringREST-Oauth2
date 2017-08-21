package com.ortec.ihm.clktime.rest.util;

import com.ortec.ihm.clktime.rest.common.user.TokenedUser;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @Author: romain.pillot
 * @Date: 21/08/2017
 */
public final class SessionUtil {
    public static TokenedUser activeUser() {
        return (TokenedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
