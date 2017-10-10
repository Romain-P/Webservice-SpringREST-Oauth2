package com.ortec.gta.database.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.persistence.Entity;
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
    private Long id;
    private String code;
    private String name;

    @Column(name="creation_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="modification_date")
    private Date modificationDate;

    @Temporal(TemporalType.TIMESTAMP)
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
