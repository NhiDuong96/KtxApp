package com.example.minhnhi.quanlyktx.beans;

import com.google.gson.annotations.SerializedName;

public class Area {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("numberFloor")
    private int numFloor;
    @SerializedName("status")
    private int status;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getNumFloor() {
        return numFloor;
    }

    public int getStatus() {
        return status;
    }
}
