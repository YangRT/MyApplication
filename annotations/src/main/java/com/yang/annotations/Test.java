package com.yang.annotations;

import com.yang.annotations.MyAnnotation;

public class Test {

    @MyAnnotation(value = "192.168.0.1")
    public String getIpMsg(){
        return "";
    }
}
