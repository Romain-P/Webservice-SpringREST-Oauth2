package com.ortec.gta.service;

/**
 * @Author: romain.pillot
 * @Date: 10/10/2017
 */
public interface SecurityService {
    boolean isSuperiorOf(Long requestUserId, Long targetUserId);
}
