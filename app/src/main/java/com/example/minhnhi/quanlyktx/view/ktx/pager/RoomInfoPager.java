package com.example.minhnhi.quanlyktx.view.ktx.pager;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.minhnhi.quanlyktx.R;
import com.example.minhnhi.quanlyktx.beans.Area;
import com.example.minhnhi.quanlyktx.beans.Room;

public class RoomInfoPager extends RoomPager {
    private String roomAds;
    private int numBed;
    private int numSV;
    private int numRegister;
    private String price;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadData(room);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.room_info_pager, container, false);
        TextView t1,t2,t3,t4,t5;
        t1 = view.findViewById(R.id.address); t1.setText(roomAds);
        t2 = view.findViewById(R.id.numBed); t2.setText(String.valueOf(numBed));
        t3 = view.findViewById(R.id.numSV); t3.setText(String.valueOf(numSV));
        t4 = view.findViewById(R.id.numRegister); t4.setText(String.valueOf(numRegister));
        t5 = view.findViewById(R.id.price); t5.setText(price);
        return view;
    }

    public void loadData(Room room) {
        if(room == null || room.getFloor() == null) return;
        Area area = room.getFloor().getArea();
        roomAds = "Phòng " + room.getName() + " " + area.getName();
        numBed = room.getNumberBed();
        numSV = room.getStudentPresent();
        numRegister = room.getStudentRegister();
        price = room.getRoomCost() + " VNĐ";
    }

}
