package com.ortec.gta.shared.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @Author: romain.pillot
 * @Date: 26/09/2017
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StringUtil {
    public static boolean isNullOrEmpty(Object[] array) {
        return array == null || array.length == 0;
    }
}
