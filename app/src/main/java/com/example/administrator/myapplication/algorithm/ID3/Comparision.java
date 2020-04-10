package com.example.administrator.myapplication.algorithm.ID3;

import java.util.Comparator;

public class Comparision implements Comparator {
    @Override
    public int compare(Object a, Object b) {
        String str1 = (String)a;
        String str2 = (String)b;
        return str1.compareTo(str2);
    }
}
