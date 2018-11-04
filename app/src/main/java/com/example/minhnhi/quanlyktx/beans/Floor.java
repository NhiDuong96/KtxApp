package com.example.minhnhi.quanlyktx.beans;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Floor {
    @SerializedName("id")
    private int id;
    @SerializedName("areaId")
    private int areaId;
    @SerializedName("name")
    private String name;
    @SerializedName("status")
    private int status;

    private List<Room> rooms;
    private Area area;

    public int getId() {
        return id;
    }

    public int getAreaId() {
        return areaId;
    }

    public String getName() {
        return name;
    }

    public int getStatus() {
        return status;
    }

    public List<Room> getRooms() {
        if(rooms == null) rooms = new ArrayList<>();
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }
}
