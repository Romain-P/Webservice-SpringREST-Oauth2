package com.ortec.gta.database.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Author: romain.pillot
 * @Date: 24/08/2017
 */
@Entity
@Accessors(chain = true)
@Getter @Setter
public class Week {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "week_number")
    private int weekNumber;
    private int year;
    private int monday;
    private int tuesday;
    private int wednesday;
    private int thursday;
    private int friday;
    private int saturday;
    private int sunday;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "activity_id", referencedColumnName= "id")
    private Activity activity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName= "id")
    private User user;
}
