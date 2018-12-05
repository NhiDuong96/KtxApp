package com.example.minhnhi.quanlyktx.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterRoom {
    @SerializedName("id")
    private int id;

    @SerializedName("user_id")
    private int userId;

    @SerializedName("number")
    private int numRegister;

    @SerializedName("semester_id")
    private int semesterId;

    @SerializedName("room_id")
    private int roomId;

    @SerializedName("year")
    private String year;

    @SerializedName("status")
    private int status;

    @SerializedName("time_censor")
    private String timeCensor;

    @SerializedName("time_register")
    private String timeRegister;

    @Expose
    private Room room;

    public RegisterRoom(){}

    public RegisterRoom(Room room){
        this.room = room;
        this.roomId = room.getId();
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getNumRegister() {
        return numRegister;
    }

    public void setNumRegister(int numRegister) {
        this.numRegister = numRegister;
    }

    public int getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(int semesterId) {
        this.semesterId = semesterId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTimeCensor() {
        return timeCensor;
    }

    public void setTimeCensor(String timeCensor) {
        this.timeCensor = timeCensor;
    }

    public String getTimeRegister() {
        return timeRegister;
    }

    public void setTimeRegister(String timeRegister) {
        this.timeRegister = timeRegister;
    }

    @Override
    public String toString() {
        return "RegisterRoom{" +
                "id=" + id +
                ", userId=" + userId +
                ", numRegister=" + numRegister +
                ", semesterId=" + semesterId +
                ", roomId=" + roomId +
                ", year='" + year + '\'' +
                ", status=" + status +
                ", timeCensor='" + timeCensor + '\'' +
                ", timeRegister='" + timeRegister + '\'' +
                '}';
    }
}
