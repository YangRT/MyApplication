package com.example.administrator.myapplication.algorithm;

public class ReverseWords {

    public static void main(String[] args) {
        String s = "  hello world!  ";
        String t = reverseWords(s);
        System.out.println(t+"5");
    }

    public static String reverseWords(String s) {
        String[] result = s.split(" ");
        StringBuilder builder = new StringBuilder();
        for(int i = result.length-1;i >= 0;i--){
            if(!result[i].equals("")){
                builder.append(result[i]);
                builder.append(" ");
            }
        }
        String t = builder.toString();
        if(t.endsWith(" ")){
            t = t.substring(0,t.length()-1);
        }
        return t;
    }

}
