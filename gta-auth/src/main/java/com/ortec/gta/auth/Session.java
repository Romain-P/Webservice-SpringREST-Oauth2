package com.ortec.gta.auth;

import java.lang.annotation.*;

/**
 * Annotate a controller method parameter with @Tokened
 * will inject the current authenticated user
 */

@Target({ ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Session {}