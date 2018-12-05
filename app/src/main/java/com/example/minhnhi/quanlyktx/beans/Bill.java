package com.example.minhnhi.quanlyktx.beans;

import com.google.gson.annotations.SerializedName;

public class Bill {
    @SerializedName("id")
    private int id;
    @SerializedName("month")
    private int month;
    @SerializedName("year")
    private int year;
    @SerializedName("level_water")
    private int level_water;
    @SerializedName("level_elec")
    private int level_elec;
    @SerializedName("total")
    private float total;
    @SerializedName("room_id")
    private int roomId;
    @SerializedName("new_number_water")
    private int new_number_water;
    @SerializedName("new_number_elec")
    private int new_number_elec;
    @SerializedName("old_number_water")
    private int old_number_water;
    @SerializedName("old_number_elec")
    private int old_number_elec;
    @SerializedName("cost_water")
    private float cost_water;
    @SerializedName("cost_elec")
    private float cost_elec;
    @SerializedName("total_water")
    private float total_water;
    @SerializedName("total_elec")
    private float total_elec;
    @SerializedName("status")
    private int status;

    public class BillStatus {
        public static final int DEBT = 0;
        public static final int PAID = 1;
    }

    public Bill(){
        level_elec = level_water = 0;
        new_number_elec = new_number_water = 0;
        old_number_elec = old_number_water = 0;
        cost_elec = cost_water = 0;
        total_elec = total_water = 0;
        total = 0;
        status = BillStatus.DEBT;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getLevel_water() {
        return level_water;
    }

    public void setLevel_water(int level_water) {
        this.level_water = level_water;
    }

    public int getLevel_elec() {
        return level_elec;
    }

    public void setLevel_elec(int level_elec) {
        this.level_elec = level_elec;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getNew_number_water() {
        return new_number_water;
    }

    public void setNew_number_water(int new_number_water) {
        this.new_number_water = new_number_water;
    }

    public int getNew_number_elec() {
        return new_number_elec;
    }

    public void setNew_number_elec(int new_number_elec) {
        this.new_number_elec = new_number_elec;
    }

    public int getOld_number_water() {
        return old_number_water;
    }

    public void setOld_number_water(int old_number_water) {
        this.old_number_water = old_number_water;
    }

    public int getOld_number_elec() {
        return old_number_elec;
    }

    public void setOld_number_elec(int old_number_elec) {
        this.old_number_elec = old_number_elec;
    }

    public float getCost_water() {
        return cost_water;
    }

    public void setCost_water(float cost_water) {
        this.cost_water = cost_water;
    }

    public float getCost_elec() {
        return cost_elec;
    }

    public void setCost_elec(float cost_elec) {
        this.cost_elec = cost_elec;
    }

    public float getTotal_water() {
        return total_water;
    }

    public void setTotal_water(float total_water) {
        this.total_water = total_water;
    }

    public float getTotal_elec() {
        return total_elec;
    }

    public void setTotal_elec(float total_elec) {
        this.total_elec = total_elec;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
