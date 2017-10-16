package com.ortec.gta.domain;

import java.util.Set;

/**
 * @Author: romain.pillot
 * @Date: 26/09/2017
 */
public interface UserIdentity {
    Long getId();
    Set<String> getSessionRoles();
}
