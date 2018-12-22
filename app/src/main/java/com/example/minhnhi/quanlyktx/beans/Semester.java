package com.example.minhnhi.quanlyktx.beans;

import com.google.gson.annotations.SerializedName;

public class Semester {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("month")
    private int month;
    @SerializedName("monthBegin")
    private int monthBegin;
    @SerializedName("monthEnd")
    private int monthEnd;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getMonthBegin() {
        return monthBegin;
    }

    public void setMonthBegin(int monthBegin) {
        this.monthBegin = monthBegin;
    }

    public int getMonthEnd() {
        return monthEnd;
    }

    public void setMonthEnd(int monthEnd) {
        this.monthEnd = monthEnd;
    }
}
