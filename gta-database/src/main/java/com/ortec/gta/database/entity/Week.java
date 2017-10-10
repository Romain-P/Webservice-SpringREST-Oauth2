package com.ortec.gta.database.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.persistence.Entity;

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
    private Long id;
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
