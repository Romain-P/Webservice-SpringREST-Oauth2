package com.ortec.ihm.clktime.rest.database.model.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

/**
 * @Author: romain.pillot
 * @Date: 31/07/2017
 */
@Entity
@Accessors(chain = true)
@Table(name = "role_template")
@Getter @Setter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
}
