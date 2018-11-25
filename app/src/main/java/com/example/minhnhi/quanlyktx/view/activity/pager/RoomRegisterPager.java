package com.example.minhnhi.quanlyktx.view.activity.pager;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.minhnhi.quanlyktx.R;
import com.example.minhnhi.quanlyktx.beans.Area;
import com.example.minhnhi.quanlyktx.beans.RegisterRoom;
import com.example.minhnhi.quanlyktx.beans.Room;
import com.example.minhnhi.quanlyktx.view.activity.RoomRegisterDetailsFragment;
import com.example.minhnhi.quanlyktx.view.ktx.pager.RoomPager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RoomRegisterPager extends Fragment implements RoomPager, View.OnClickListener {

    private String roomAds;
    private int numBed;
//    private int numSV;
    private int numRegister;
    private String price;
    private int maxBed;
    private Room room;

    private Spinner spinner;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadData(room);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.room_register_info_pager, container, false);
        TextView t1,t2,t3,t4,t5;
        t1 = view.findViewById(R.id.address); t1.setText(roomAds);
        t2 = view.findViewById(R.id.numBed); t2.setText(String.valueOf(numBed));
        //t3 = view.findViewById(R.id.numSV); t3.setText(String.valueOf(numSV));
        t4 = view.findViewById(R.id.numRegister); t4.setText(String.valueOf(numRegister));
        t5 = view.findViewById(R.id.price); t5.setText(price);
        view.findViewById(R.id.register).setOnClickListener(this);

        spinner = view.findViewById(R.id.num);
        List<Integer> numList = new ArrayList<>();
        for(int i = 1; i <= maxBed; i++){
            numList.add(i);
        }
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_item, numList);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);


        return view;
    }

    public void loadData(Room room) {
        if(room == null || room.getFloor() == null) return;
        Area area = room.getFloor().getArea();
        roomAds = "Phòng " + room.getName() + " " + area.getName();
        numBed = room.getNumberBed();
        //numSV = room.getStudentPresent();
        numRegister = room.getStudentRegister();
        maxBed = room.getStudentMax();
        price = "100000 VNĐ";
    }

    @Override
    public void setRoom(Room room) {
        this.room = room;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.register:
                RoomRegisterDetailsFragment fragment = (RoomRegisterDetailsFragment) getParentFragment();
                Date date = new Date();

                RegisterRoom registerRoom = new RegisterRoom(room);
                registerRoom.setNumRegister((Integer) spinner.getSelectedItem());
                registerRoom.setYear(String.valueOf(date.getYear()));
                registerRoom.setTimeRegister(date.toGMTString());
                assert fragment != null;
                fragment.onRegisterSuccess(registerRoom);
                break;
        }
    }
}
