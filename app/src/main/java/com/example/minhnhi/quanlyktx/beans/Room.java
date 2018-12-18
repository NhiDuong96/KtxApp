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

    @SerializedName("floorName")
    private String floorName;
    @SerializedName("functionName")
    private String functionName;
    @SerializedName("areaName")
    private String areaName;
    @SerializedName("costName")
    private String costName;
    @SerializedName("costValue")
    private String costValue;
    @SerializedName("costLevel")
    private String costLevel;

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

    public Floor getFloor() {
        return floor;
    }

    public void setFloor(Floor floor) {
        this.floor = floor;
    }

    public String getRoomCost(){
        return getCostValue() + " " + getCostName();
    }

    public String getFloorName() {
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getCostName() {
        return costName;
    }

    public void setCostName(String costName) {
        this.costName = costName;
    }

    public String getCostValue() {
        return costValue;
    }

    public void setCostValue(String costValue) {
        this.costValue = costValue;
    }

    public String getCostLevel() {
        return costLevel;
    }

    public void setCostLevel(String costLevel) {
        this.costLevel = costLevel;
    }
}
