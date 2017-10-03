package com.ortec.gta.service;

import com.google.common.collect.Sets;
import com.ortec.gta.database.UserRepositoryImpl;
import com.ortec.gta.database.model.dto.AbsenceDayDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @Author: romain.pillot
 * @Date: 03/10/2017
 */
@Service
public class AgirService {
    @Value("${agir.url_format}")
    private String formatUrl;

    @Autowired
    private HttpService httpService;

    @Autowired
    private UserRepositoryImpl userRepository;

    public Set<AbsenceDayDTO> getAbsenceDays(int userId, int year) {
        return userRepository.findById(userId)
                .map(x -> httpService
                        .get(formatUrl, x.getFixedId(), year + "0101", year + "3112")
                        .toResponseSet(AbsenceDayDTO[].class))
                .orElseGet(Sets::newHashSet);
    }
}
