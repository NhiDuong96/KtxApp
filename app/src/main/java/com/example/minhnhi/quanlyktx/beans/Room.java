package com.example.minhnhi.quanlyktx.beans;

public class Room {
    private int roomNumber;
    private int currentUsers;
    private int maxUsers;
    private int status;

    public Room(int roomNumber, int currentUsers, int maxUsers, int status) {
        this.roomNumber = roomNumber;
        this.currentUsers = currentUsers;
        this.maxUsers = maxUsers;
        this.status = status;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getCurrentUsers() {
        return currentUsers;
    }

    public void setCurrentUsers(int currentUsers) {
        this.currentUsers = currentUsers;
    }

    public int getMaxUsers() {
        return maxUsers;
    }

    public void setMaxUsers(int maxUsers) {
        this.maxUsers = maxUsers;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String toNumberUsed(){
        return currentUsers + "/" + maxUsers;
    }
}
