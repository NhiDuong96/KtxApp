package com.example.minhnhi.quanlyktx.view.ktx;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.minhnhi.quanlyktx.R;
import com.example.minhnhi.quanlyktx.beans.Area;
import com.example.minhnhi.quanlyktx.beans.Floor;
import com.example.minhnhi.quanlyktx.beans.Room;
import com.example.minhnhi.quanlyktx.cmd.ApiMethod;
import com.example.minhnhi.quanlyktx.cmd.ApiResponse;
import com.example.minhnhi.quanlyktx.cmd.ApiResponseClass;
import com.example.minhnhi.quanlyktx.cmd.BaseMsg;
import com.example.minhnhi.quanlyktx.cmd.ErrorCode;
import com.example.minhnhi.quanlyktx.utils.OnSlideAnimationStartListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class KtxInfoFragment extends Fragment implements RoomAdapter.OnRoomClickListener {
    private FloorAdapter floorAdapter;

    private List<Area> areaList = new ArrayList<>();
    private List<Floor> floorList = new ArrayList<>();

    private OnSlideAnimationStartListener<Room> startAnimation;

    private HashMap<String, Area> areasView = new HashMap<>();

    private boolean isLoaded = false;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prepareData();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;
        if(isLoaded) {
            view = inflater.inflate(R.layout.building_info_fragment, container, false);
        }
        else{
            view = inflater.inflate(R.layout.building_loading_field_layout, container, false);
            view.findViewById(R.id.back).setOnClickListener(v -> {
               getActivity().finish();
            });
            return view;
        }

        Spinner areasName = view.findViewById(R.id.areasName);
        ArrayAdapter<Object> arrayAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, getListAreasName().keySet().toArray());
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        areasName.setAdapter(arrayAdapter);
        areasName.setOnItemSelectedListener(selectedListener);
        RecyclerView recyclerView = view.findViewById(R.id.floorList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        floorAdapter = getFloorAdapter(getContext(), floorList);
        floorAdapter.setOnRoomClickListener(KtxInfoFragment.this);
        recyclerView.setAdapter(floorAdapter);
        return view;
    }

    public FloorAdapter getFloorAdapter(Context context, List<Floor> floors){
        return new FloorAdapter(context, floors);
    }

    AdapterView.OnItemSelectedListener selectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            Object name = adapterView.getItemAtPosition(i);
            loadFloors(areasView.get(name));
            for(Floor floor: floorList){
                loadRooms(floor);
            }
            floorAdapter.notifyDataSetChanged();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    public void setClickListener(OnSlideAnimationStartListener<Room> startAnimation) {
        this.startAnimation = startAnimation;
    }

    public void prepareData(){
        if(loadAreas()){
            isLoaded = true;
        }
        else{
            isLoaded = false;
            Toast.makeText(this.getContext(), "Load thông tin Ktx thất bại", Toast.LENGTH_LONG).show();
        }
    }

    private HashMap<String, Area> getListAreasName(){
        for(Area area: areaList){
            areasView.put(area.getName(), area);
        }
        return areasView;
    }

    private boolean loadAreas(){
        Resources res = getResources();
        String uri = res.getString(R.string.host) + res.getString(R.string.get_areas_uri);

        BaseMsg<List<Area>> msg = new BaseMsg<>(uri, ApiMethod.GET, ApiResponseClass.AreaResponse.class);
        msg.resolveDataOnMainThread();

        if(msg.getCode() == ErrorCode.SUCCESS){
            areaList.addAll(msg.getData());
            return true;
        }
        return false;
    }

    private void loadFloors(Area area){
        floorList.clear();
        Resources res = getResources();
        String uri = res.getString(R.string.host) +
                res.getString(R.string.get_floors_uri) + String.valueOf(area.getId());

        BaseMsg<List<Floor>> msg = new BaseMsg<>(uri, ApiMethod.GET, ApiResponseClass.FloorResponse.class);
        msg.resolveDataOnMainThread();

        if(msg.getCode() == ErrorCode.SUCCESS){
            floorList.addAll(msg.getData());
            for(Floor floor: floorList){
                floor.setArea(area);
            }
        }
    }

    private void loadRooms(Floor floor){
        Resources res = getResources();
        String uri = res.getString(R.string.host) +
                res.getString(R.string.get_rooms_uri) +
                String.valueOf(floor.getId());

        BaseMsg<List<Room>> msg = new BaseMsg<>(uri, ApiMethod.GET, ApiResponseClass.RoomResponse.class);
        msg.resolveDataOnMainThread();

        if(msg.getCode() == ErrorCode.SUCCESS){
            List<Room> roomList = msg.getData();
            for(Room room: roomList){
                room.setFloor(floor);
            }
            floor.setRooms(roomList);
        }
    }

    @Override
    public void onRoomClick(Room room) {
        startAnimation.onAnimationStart(room);
    }
}
