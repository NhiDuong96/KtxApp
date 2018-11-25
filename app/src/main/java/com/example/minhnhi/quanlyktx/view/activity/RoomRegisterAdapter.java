package com.example.minhnhi.quanlyktx.view.activity;

import com.example.minhnhi.quanlyktx.beans.Room;
import com.example.minhnhi.quanlyktx.view.ktx.RoomAdapter;

import java.util.List;

public class RoomRegisterAdapter extends RoomAdapter {

    public RoomRegisterAdapter(List<Room> rooms) {
        super(rooms);
    }

    @Override
    public int getCurrentStudent(Room room) {
        return room.getStudentRegister();
    }
}
