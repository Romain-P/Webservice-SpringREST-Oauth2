package com.ortec.gta.shared.util;

import com.lambdista.util.Try;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Optional;

/**
 * @Author: romain.pillot
 * @Date: 03/08/2017
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ReflectUtil {
    @SuppressWarnings("unchecked")
    public static <T> Optional<T> get(Object instance, String method) {
        return Try.apply(() -> (T) instance.getClass().getMethod(method).invoke(instance)).toOptional();
    }

    public static boolean set(Object instance, String method, Object param) {
        return Try.apply(() -> instance.getClass().getMethod(method, param.getClass()).invoke(instance, param)).isSuccess();
    }
}
