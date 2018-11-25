package com.example.minhnhi.quanlyktx.beans;

import com.google.gson.annotations.SerializedName;

public class Room {

    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("areaId")
    private int areaId;
    @SerializedName("floorId")
    private int floorId;
    @SerializedName("numberBed")
    private int numberBed;
    @SerializedName("gender")
    private int gender;
    @SerializedName("costId")
    private int costId;
    @SerializedName("status")
    private int status;
    @SerializedName("studentMax")
    private int studentMax;
    @SerializedName("studentPresent")
    private int studentPresent;
    @SerializedName("studentRegister")
    private int studentRegister;
    @SerializedName("functionId")
    private int functionId;

    private Floor floor;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAreaId() {
        return areaId;
    }

    public int getFloorId() {
        return floorId;
    }

    public int getNumberBed() {
        return numberBed;
    }

    public int getGender() {
        return gender;
    }

    public int getCostId() {
        return costId;
    }

    public int getStatus() {
        return status;
    }

    public int getStudentMax() {
        return studentMax;
    }

    public int getStudentPresent() {
        return studentPresent;
    }

    public int getStudentRegister() {
        return studentRegister;
    }

    public int getFunctionId() {
        return functionId;
    }

    public String getUsedNumber(){
        return String.valueOf(studentPresent) + "/" + String.valueOf(studentMax);
    }

    public Floor getFloor() {
        return floor;
    }

    public void setFloor(Floor floor) {
        this.floor = floor;
    }
}
