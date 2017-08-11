package com.ortec.ihm.clktime.rest.database.model.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.Set;

/**
 * @Author: romain.pillot
 * @Date: 10/08/2017
 */
@Accessors(chain = true)
@Getter @Setter
public class ActivityDTO {
    private Integer id;
    private String name;
    private long creationDate;
    private long modificationDate;
    private long deletionDate;
    private UserDTO lastEditor;
    private ActivityDTO parentActivity;
    private Set<ActivityDTO> subActivities;
    @JsonManagedReference
    private Set<UserDTO> users;
}
