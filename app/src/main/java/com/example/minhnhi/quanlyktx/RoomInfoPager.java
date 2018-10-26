package com.example.minhnhi.quanlyktx;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.minhnhi.quanlyktx.utils.RoomPager;

public class RoomInfoPager extends Fragment implements RoomPager {

    private String roomAds;
    private int numBed;
    private int numSV;
    private int numRegister;
    private String price;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.room_info_pager_1, container, false);
        TextView t1,t2,t3,t4,t5;
        t1 = view.findViewById(R.id.address); t1.setText(roomAds);
        t2 = view.findViewById(R.id.numBed); t2.setText(String.valueOf(numBed));
        t3 = view.findViewById(R.id.numSV); t3.setText(String.valueOf(numSV));
        t4 = view.findViewById(R.id.numRegister); t4.setText(String.valueOf(numRegister));
        t5 = view.findViewById(R.id.price); t5.setText(price + " VNĐ");
        return view;
    }


    @Override
    public void loadData(int roomID) {
        roomAds = "Phong 104 Nha 4";
        numBed = 8;
        numSV = 4;
        numRegister = 0;
        price = "140000";
    }

}
