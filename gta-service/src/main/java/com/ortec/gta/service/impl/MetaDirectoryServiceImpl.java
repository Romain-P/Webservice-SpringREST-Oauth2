package com.ortec.gta.service.impl;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.ortec.gta.domain.UserDTO;
import com.ortec.gta.service.HttpService;
import com.ortec.gta.service.MetaDirectoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * @Author: romain.pillot
 * @Date: 12/09/2017
 */

@Service
public class MetaDirectoryServiceImpl implements MetaDirectoryService {
    private final String formatUrl;
    private final Map<Long, UserDTO> cache;

    @Autowired
    private HttpService httpService;

    public MetaDirectoryServiceImpl(@Value("${meta-directory.url}") String url) {
        this.formatUrl = url + (url.endsWith("/") ? "%s %s" : "/%s %s");
        this.cache = Maps.newHashMap();
    }

    public Optional<UserDTO> findUserDetails(UserDTO user) {
        return Optional.ofNullable(cache.computeIfAbsent(user.getId(), id -> {
            UserDTO[] found = httpService
                    .get(formatUrl, user.getName(), user.getLastname())
                    .toResponse(UserDTO[].class);
            return found.length > 0 ? found[0] : null;
        }));
    }

    /**
     * Retrieve a meta-directory user without caching
     */
    @Deprecated
    public Optional<UserDTO> findUserDetails(String name, String lastname) {
        UserDTO[] found = httpService
                .get(formatUrl, name, lastname)
                .toResponse(UserDTO[].class);
        return Optional.ofNullable(found.length > 0 ? found[0] : null);
    }

    public Set<UserDTO> getUserChildren(UserDTO user) {
        return findUserDetails(user)
                .map(UserDTO::getChildren)
                .orElseGet(Sets::newHashSet);
    }

    public Optional<UserDTO> getUserParent(UserDTO user) {
        return findUserDetails(user).map(UserDTO::getSuperior);
    }
}
