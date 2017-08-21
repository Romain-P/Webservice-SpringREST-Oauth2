package com.ortec.ihm.clktime.rest.database.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * @Author: romain.pillot
 * @Date: 10/08/2017
 */

@Entity
@Accessors(chain = true)
@Getter @Setter
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String code;
    private String name;
    @Column(name="creation_date")
    private Date creationDate;
    @Column(name="modification_date")
    private Date modificationDate;
    @Column(name="deletion_date")
    private Date deletionDate;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_activity")
    private Set<Activity> subActivities;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_activity", referencedColumnName = "id")
    private Activity parentActivity;

    @ManyToMany(mappedBy = "activities", fetch = FetchType.EAGER)
    private Set<User> users;

    @ManyToOne
    @JoinColumn(name="last_user_editor")
    private User lastEditor;

    private boolean active;
}
