package com.ortec.gta.util;

import com.ortec.gta.common.user.TokenedUser;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @Author: romain.pillot
 * @Date: 21/08/2017
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SessionUtil {
    public static TokenedUser activeUser() {
        return (TokenedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
