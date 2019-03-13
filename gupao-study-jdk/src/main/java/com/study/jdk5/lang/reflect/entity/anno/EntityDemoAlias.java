package com.study.jdk5.lang.reflect.entity.anno;

import com.study.jdk5.lang.reflect.entity.Member;

@EntityDemo(clazz= Member.class)
public @interface EntityDemoAlias {

    String value() default "";
}
