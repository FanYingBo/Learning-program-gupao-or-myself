package com.study.selfs.jdk5.beans.beaninfo;

import com.study.selfs.jdk5.beans.beandescriptor.bean.User;

import java.beans.*;

/**
 * @see java.beans.BeanInfo
 */
public class BeanInfoDemo {

    public static void main(String[] args) {
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(User.class);
            MethodDescriptor[] methodDescriptors = beanInfo.getMethodDescriptors();
            for(int i=0;i<methodDescriptors.length;i++){
                System.out.println("methoddescription name："+methodDescriptors[i].getName());
            }
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for(int i=0;i<propertyDescriptors.length;i++){
                System.out.println("propertyDescriptor name："+propertyDescriptors[i].getName());
            }
            BeanInfo[] additionalBeanInfo = beanInfo.getAdditionalBeanInfo();
//            for(int i=0;i<additionalBeanInfo.length;i++){
//                System.out.println("propertyDescriptor name："+additionalBeanInfo[i]);
//            }
            // 获取bean的描述
            BeanDescriptor beanDescriptor = beanInfo.getBeanDescriptor();
            // 获取bean 的类名
            Class<?> beanClass = beanDescriptor.getBeanClass();
            System.out.println("beanclass："+beanClass);
            // 类名
            String displayName = beanDescriptor.getDisplayName();
            System.out.println("displayName："+displayName);
            // 类名
            System.out.println("name："+beanDescriptor.getName());


        } catch (IntrospectionException e) {
            e.printStackTrace();
        }

    }
}
