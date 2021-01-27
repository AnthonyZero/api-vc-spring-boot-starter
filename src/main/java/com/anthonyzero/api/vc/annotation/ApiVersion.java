package com.anthonyzero.api.vc.annotation;

import java.lang.annotation.*;

/**
 * API版本控制注解
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiVersion {
    /**
     * 版本。x.y.z格式
     *
     * @return
     */
    String value() default "1.0.0";
}
