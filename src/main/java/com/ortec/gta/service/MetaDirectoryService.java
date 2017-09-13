package com.ortec.gta.service;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.ortec.gta.database.model.dto.UserDTO;
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
public class MetaDirectoryService {
    private final String formatUrl;
    private final Map<Integer, UserDTO> cache;

    @Autowired
    private HttpService httpService;

    public MetaDirectoryService(@Value("${meta-directory.url}") String url) {
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
     * dev method, to remove in production mode
     */
    public Optional<UserDTO> findUserDetailsHacky(String name, String lastname) {
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
