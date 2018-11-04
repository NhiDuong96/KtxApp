package com.example.minhnhi.quanlyktx.beans;

import com.google.gson.annotations.SerializedName;

public class Bill {
    @SerializedName("id")
    private int id;
    @SerializedName("month")
    private int month;
    @SerializedName("year")
    private int year;
    @SerializedName("oldNumber")
    private int oldNumber;
    @SerializedName("newNumber")
    private int newNumber;
    @SerializedName("total")
    private float total;
    @SerializedName("type")
    private int type;
    @SerializedName("roomId")
    private int roomId;
    @SerializedName("costId")
    private int costId;
    @SerializedName("level")
    private int level;
    @SerializedName("status")
    private int status;

    public class BillType{
        public static final int ELECTRIC_BILL = 0;
        public static final int WATER_BILL = 1;
    }

    public class BillStatus {
        public static final int DEBT = 0;
        public static final int PAID = 1;
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

    public int getOldNumber() {
        return oldNumber;
    }

    public void setOldNumber(int oldNumber) {
        this.oldNumber = oldNumber;
    }

    public int getNewNumber() {
        return newNumber;
    }

    public void setNewNumber(int newNumber) {
        this.newNumber = newNumber;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getCostId() {
        return costId;
    }

    public void setCostId(int costId) {
        this.costId = costId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
