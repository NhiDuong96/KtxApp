package com.example.minhnhi.quanlyktx.beans;

import com.example.minhnhi.quanlyktx.utils.TimeParser;

import java.util.Date;

public class UserProfile {
    private String mssv;
    private String name;
    private String gentle;
    private String year;
    private String birthday;
    private String cmnd;

    public String getMssv() {
        return mssv;
    }

    public void setMssv(String mssv) {
        this.mssv = mssv;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGentle() {
        return gentle;
    }

    public void setGentle(String gentle) {
        this.gentle = gentle;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCmnd() {
        return cmnd;
    }

    public void setCmnd(String cmnd) {
        this.cmnd = cmnd;
    }

    public static UserProfile parseJson(String json){
        UserProfile profile = new UserProfile();
        profile.mssv = "102140032";
        profile.name = "Duong Minh Nhi";
        profile.gentle = "Nam";
        profile.year = "2014";
        profile.birthday = TimeParser.parse(new Date(), TimeParser.DATE_PATTERN_1);
        profile.cmnd = "230995990";
        return profile;
    }
}
