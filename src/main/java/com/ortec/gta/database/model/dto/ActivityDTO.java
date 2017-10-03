package com.ortec.gta.database.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.collect.Sets;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author: romain.pillot
 * @Date: 10/08/2017
 */
@Accessors(chain = true)
@Getter @Setter
public final class ActivityDTO {
    private Integer id;
    private String name;
    private long creationDate;
    private long modificationDate;
    private long deletionDate;
    @JsonIgnoreProperties(value = {"activities", "children"})
    private UserDTO lastEditor;
    @JsonIgnoreProperties(value = {"subActivities", "users", "parentActivity"})
    private ActivityDTO parentActivity;
    @JsonIgnoreProperties(value = {"parentActivity"})
    private Set<ActivityDTO> subActivities;
    @JsonIgnoreProperties(value = {"activities"})
    private Set<UserDTO> users;
    private String code;
    private boolean active;
}
