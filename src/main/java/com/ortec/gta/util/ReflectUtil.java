package com.ortec.gta.util;

import com.lambdista.util.Try;

import java.util.Optional;

/**
 * @Author: romain.pillot
 * @Date: 03/08/2017
 */
public final class ReflectUtil {
    private ReflectUtil() {}

    @SuppressWarnings("unchecked")
    public static <T> Optional<T> get(Object instance, String method) {
        return Try.apply(() -> (T) instance.getClass().getMethod(method).invoke(instance)).toOptional();
    }

    public static boolean set(Object instance, String method, Object param) {
        return Try.apply(() -> instance.getClass().getMethod(method, param.getClass()).invoke(instance, param)).isSuccess();
    }
}
