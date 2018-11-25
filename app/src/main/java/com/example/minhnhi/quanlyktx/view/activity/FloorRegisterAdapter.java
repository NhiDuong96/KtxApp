package com.example.minhnhi.quanlyktx.view.activity;

import android.content.Context;

import com.example.minhnhi.quanlyktx.beans.Floor;
import com.example.minhnhi.quanlyktx.beans.Room;
import com.example.minhnhi.quanlyktx.view.ktx.FloorAdapter;
import com.example.minhnhi.quanlyktx.view.ktx.RoomAdapter;

import java.util.List;

public class FloorRegisterAdapter extends FloorAdapter {
    public FloorRegisterAdapter(Context context, List<Floor> floors) {
        super(context, floors);
    }

    @Override
    public RoomAdapter getRoomAdapter(List<Room> rooms){
        return new RoomRegisterAdapter(rooms);
    }
}
