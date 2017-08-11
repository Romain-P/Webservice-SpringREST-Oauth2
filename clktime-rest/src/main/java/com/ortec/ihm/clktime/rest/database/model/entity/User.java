package com.ortec.ihm.clktime.rest.database.model.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * @Author: romain.pillot
 * @Date: 28/07/2017
 */
@Entity
@Accessors(chain = true)
@Table(name = "ortec_user")
@Getter @Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String password;
    private String name;
    private String lastname;
    private byte[] avatar;
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "role_assign",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "activity_assign",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "activity_id", referencedColumnName = "id"))
    private Set<Activity> activities;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id", referencedColumnName= "id")
    private User superior;
}
