package com.ortec.gta.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Sets;
import com.ortec.gta.database.ActivityRepositoryImpl;
import com.ortec.gta.database.UserRepositoryImpl;
import com.ortec.gta.database.model.dto.ActivityDTO;
import com.ortec.gta.database.model.dto.StatDTO;
import com.ortec.gta.database.model.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @Author: romain.pillot
 * @Date: 14/09/2017
 */

@Service
public class StatService {
    @Autowired
    private UserRepositoryImpl userRepository;

    @Autowired
    private ActivityRepositoryImpl activityRepository;

    private final Cache<String, Set> listCache;
    private final Cache<String, Long> statCache;

    public StatService() {
        this.listCache = CacheBuilder.newBuilder()
                .expireAfterWrite(1, TimeUnit.DAYS)
                .build();

        this.statCache = CacheBuilder.newBuilder()
                .expireAfterWrite(1, TimeUnit.DAYS)
                .build();
    }

    public StatDTO getStatistics() {
        System.out.println(this);
        Set<UserDTO> users = getUsers();
        Set<ActivityDTO> activities = getParentActivities();

        return new StatDTO(users.size(),
                weekValids(users),
                monthValids(users),
                weekTop(activities),
                monthTop(activities));
    }

    @SneakyThrows
    @SuppressWarnings("unchecked")
    private Set<StatDTO.ActivityStatDTO> weekTop(Set<ActivityDTO> activities) {
        return listCache.get("weekTop", () -> activities.stream()
                .map(x -> {
                    long manDays = 0;
                    //TODO: calculate subactivities weeks mandays, the last week
                    return new StatDTO.ActivityStatDTO(x.getName(), manDays);
                }).collect(Collectors.toSet()));
    }

    @SneakyThrows
    @SuppressWarnings("unchecked")
    private Set<StatDTO.ActivityStatDTO> monthTop(Set<ActivityDTO> activities) {
        return listCache.get("weekTop", () -> activities.stream()
                .map(x -> {
                    long manDays = 0;
                    //TODO: calculate subactivities weeks mandays, the last month
                    return new StatDTO.ActivityStatDTO(x.getName(), manDays);
                }).collect(Collectors.toSet()));
    }

    @SneakyThrows
    private long weekValids(Set<UserDTO> users) {
        return statCache.get("week", () -> users.stream().filter(x -> {
            //TODO: users who pointed to 100% their week
            return true;
        }).count());
    }

    @SneakyThrows
    private long monthValids(Set<UserDTO> users) {
        return statCache.get("month", () -> users.stream().filter(x -> {
            //TODO: users who pointed to 100% their month
            return true;
        }).count());
    }

    @SneakyThrows
    @SuppressWarnings("unchecked")
    private Set<UserDTO> getUsers() {
        return listCache.get("users", () -> userRepository.findAll());
    }

    @SneakyThrows
    @SuppressWarnings("unchecked")
    private Set<ActivityDTO> getParentActivities() {
        return listCache.get("activities", () -> activityRepository.findParentActivities());
    }
}
