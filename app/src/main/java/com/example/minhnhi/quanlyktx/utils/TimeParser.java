package com.example.minhnhi.quanlyktx.utils;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeParser {
    public static final int DATE_PATTERN_1 = 0;
    public static final int TIME_PATTERN_1 = 10;

    @SuppressLint("SimpleDateFormat")
    public static String parse(Date date, int pattern){
        switch (pattern){
            case DATE_PATTERN_1:
                return new SimpleDateFormat("dd/MM/yyyy").format(date);
            case TIME_PATTERN_1:
                return new SimpleDateFormat("HH:mm").format(date);
        }
        return date.toGMTString();
    }
}
