package com.yang.annotations;

import java.lang.reflect.Method;

public class AnnotationProcessor {

    public static void main(String[] args) {
        Method[] methods = Test.class.getDeclaredMethods();
        for(Method method:methods){
            if (method.isAnnotationPresent(MyAnnotation.class)){
                MyAnnotation annotation = method.getAnnotation(MyAnnotation.class);
                System.out.println(annotation.value());
            }
        }
    }
}
