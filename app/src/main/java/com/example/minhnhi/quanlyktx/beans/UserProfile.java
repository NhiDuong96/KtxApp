package com.example.minhnhi.quanlyktx.beans;

import com.example.minhnhi.quanlyktx.utils.TimeParser;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class UserProfile {
    @SerializedName("mssv")
    private String mssv;
    @SerializedName("fullName")
    private String name;
    @SerializedName("gender")
    private String gender;
    @SerializedName("year")
    private String year;
    @SerializedName("birthday")
    private String birthday;
    @SerializedName("cmnd")
    private String cmnd;
    @SerializedName("className")
    private String className;
    @SerializedName("phone")
    private String phone;
    @SerializedName("address")
    private String address;

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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getBirthday() {
        if(birthday == null) return TimeParser.parse(new Date(), TimeParser.DATE_PATTERN_1);
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

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
