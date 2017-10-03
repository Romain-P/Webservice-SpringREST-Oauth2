package com.ortec.gta.database.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;
import org.springframework.cache.annotation.*;

import javax.persistence.*;
import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;
import java.util.Set;

/**
 * @Author: romain.pillot
 * @Date: 28/07/2017
 */
@Entity
@Accessors(chain = true)
@Table(name = "user_detail")
@Getter @Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "meta_id")
    private Integer metaId;
    private String username;
    private String password;
    private String name;
    private String lastname;
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles;

    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(name = "user_activity",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id", updatable=false),
            inverseJoinColumns = @JoinColumn(name = "activity_id", referencedColumnName = "id", updatable=false))
    private Set<Activity> activities;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id", referencedColumnName= "id")
    private User superior;

    @OneToMany(mappedBy="superior", fetch = FetchType.EAGER)
    private Set<User> children;
}
