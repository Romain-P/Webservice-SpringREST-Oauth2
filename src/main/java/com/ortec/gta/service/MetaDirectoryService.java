package com.ortec.gta.service;

import com.google.common.collect.Maps;
import com.ortec.gta.database.model.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;
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

    public UserDTO findUserDetails(UserDTO user) {
        return cache.computeIfAbsent(user.getId(), id ->
                httpService
                        .get(formatUrl, user.getName(), user.getLastname())
                        .toResponse(UserDTO.class));
    }

    public Set<UserDTO> getUserChildren(UserDTO user) {
        return findUserDetails(user).getChildren();
    }

    public UserDTO getUserParent(UserDTO user) {
        return findUserDetails(user).getSuperior();
    }
}
