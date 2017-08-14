package com.ortec.ihm.clktime.rest.database.model.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.Set;

/**
 * @Author: romain.pillot
 * @Date: 10/08/2017
 */
@Accessors(chain = true)
@Getter @Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ActivityDTO {
    private Integer id;
    private String name;
    private long creationDate;
    private long modificationDate;
    private long deletionDate;
    private UserDTO lastEditor;
    private ActivityDTO parentActivity;
    private Set<ActivityDTO> subActivities;
    private Set<UserDTO> users;
}
