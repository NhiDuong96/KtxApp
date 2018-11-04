package com.example.lib;

import java.util.Date;

public class test {
    public static void main(String args[]){
        Date d = new Date();
        System.out.println(d.toGMTString());
        long timestamp = d.getTime();
        System.out.println(timestamp);
        Date date = new Date(timestamp);
        System.out.println(date.toGMTString());
    }
}
