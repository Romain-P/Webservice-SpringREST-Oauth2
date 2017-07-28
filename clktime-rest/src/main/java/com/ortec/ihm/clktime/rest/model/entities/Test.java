package com.ortec.ihm.clktime.rest.model.entities;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

/**
 * @Author: romain.pillot
 * @Date: 28/07/2017
 */
@Entity
@Data
public class Test {
    @Id
    private int id;
    private int number;
}
