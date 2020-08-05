package com.study.selfs.jdk5.lang.reflect.entity.anno;

import com.study.selfs.jdk5.lang.reflect.entity.Member;

@EntityDemo(clazz= Member.class)
public @interface EntityDemoAlias {

    String value() default "";
}
