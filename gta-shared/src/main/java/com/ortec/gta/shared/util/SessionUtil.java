package com.ortec.gta.shared.util;

import com.ortec.gta.domain.UserIdentity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @Author: romain.pillot
 * @Date: 21/08/2017
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SessionUtil {
    public static UserIdentity activeUser() {
        return (UserIdentity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
