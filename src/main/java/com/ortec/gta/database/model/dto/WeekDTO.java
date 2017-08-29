package com.ortec.gta.database.model.dto;

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
    private int id;

    private ActivityDTO activity;
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
}