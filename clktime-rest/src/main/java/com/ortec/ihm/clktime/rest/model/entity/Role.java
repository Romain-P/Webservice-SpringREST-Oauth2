package com.ortec.ihm.clktime.rest.model.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

/**
 * @Author: romain.pillot
 * @Date: 31/07/2017
 */
@Entity
@Accessors(chain = true)
@Data
@Table(name = "role_template")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    @Override
    public boolean equals(Object o) {
        return o == this || (o instanceof Role && ((Role)o).name.equals(this.name));
    }
}