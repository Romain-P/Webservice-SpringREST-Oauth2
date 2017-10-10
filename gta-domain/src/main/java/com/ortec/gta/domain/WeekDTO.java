package com.ortec.gta.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @Author: romain.pillot
 * @Date: 24/08/2017
 */

@Accessors(chain = true)
@Getter @Setter
public final class WeekDTO {
    private  Long id;
    private int weekNumber;
    private int year;
    private int monday;
    private int tuesday;
    private int wednesday;
    private int thursday;
    private int friday;
    private int saturday;
    private int sunday;
    private UserDTO user;
    private ActivityDTO activity;
}