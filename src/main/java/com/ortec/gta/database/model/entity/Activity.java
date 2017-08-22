package com.ortec.gta.database.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;

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
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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

    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_activity")
    private Set<Activity> subActivities;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_activity", referencedColumnName = "id")
    private Activity parentActivity;

    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @ManyToMany(mappedBy = "activities", fetch = FetchType.EAGER)
    private Set<User> users;

    @ManyToOne
    @JoinColumn(name="last_user_editor")
    private User lastEditor;

    private boolean active;
}
