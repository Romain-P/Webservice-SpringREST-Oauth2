package com.ortec.ihm.clktime.rest.database.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.Accessors;

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
