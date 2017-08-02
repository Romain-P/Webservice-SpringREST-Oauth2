package com.ortec.ihm.clktime.rest.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @Author: romain.pillot
 * @Date: 02/08/2017
 */
public class GenericUtil {
    public static <T> Type getGenericType(Class<T> c, int index) {
        Type genericSuperClass = c.getSuperclass().getGenericSuperclass();

        ParameterizedType parametrizedType = null;
        while (parametrizedType == null)
            if ((genericSuperClass instanceof ParameterizedType))
                parametrizedType = (ParameterizedType) genericSuperClass;
            else
                genericSuperClass = ((Class<?>) genericSuperClass).getGenericSuperclass();

        return parametrizedType.getActualTypeArguments()[index];
    }
}
