package com.ortec.gta.database.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @Author: romain.pillot
 * @Date: 03/10/2017
 */
@Accessors(chain = true)
@Getter @Setter
public class AbsenceDayDTO {
    private int userId;
    private int year;
    private int day;
    private float absence_value;
    private String absence_type;
}
