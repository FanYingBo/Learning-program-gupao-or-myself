package com.study.selfs.jdk5.lang.reflect.entity.anno;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EntityDemo {
    String value() default "";
    
    Class<?> clazz();
}
