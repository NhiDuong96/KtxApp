package com.example.minhnhi.quanlyktx.view.ktx;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
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

import com.example.minhnhi.quanlyktx.R;
import com.example.minhnhi.quanlyktx.beans.Area;
import com.example.minhnhi.quanlyktx.beans.Floor;
import com.example.minhnhi.quanlyktx.beans.Room;
import com.example.minhnhi.quanlyktx.cmd.AreasResponse;
import com.example.minhnhi.quanlyktx.cmd.FloorsResponse;
import com.example.minhnhi.quanlyktx.cmd.RoomsResponse;
import com.example.minhnhi.quanlyktx.utils.JsonAPI;
import com.example.minhnhi.quanlyktx.utils.OnSlideAnimationStartListener;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class KtxInfoFragment extends Fragment implements RoomAdapter.OnRoomClickListener {
    private FloorAdapter floorAdapter;

    private List<Area> areaList = new ArrayList<>();
    private List<Floor> floorList = new ArrayList<>();

    private OnSlideAnimationStartListener<Room> startAnimation;

    private HashMap<String, Area> areasView = new HashMap<>();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("load data:", "data");
        prepareData();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.building_info_fragment, container, false);

        Spinner areasName = view.findViewById(R.id.areasName);
        ArrayAdapter<Object> arrayAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, getListAreasName().keySet().toArray());
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        areasName.setAdapter(arrayAdapter);
        areasName.setOnItemSelectedListener(selectedListener);
        RecyclerView recyclerView = view.findViewById(R.id.floorList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        Log.e("aksd", this.getClass().getName());
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
            loadRooms();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    public void setClickListener(OnSlideAnimationStartListener<Room> startAnimation) {
        this.startAnimation = startAnimation;
    }

    public void prepareData(){
        loadAreas();
    }

    private HashMap<String, Area> getListAreasName(){
        for(Area area: areaList){
            areasView.put(area.getName(), area);
        }
        return areasView;
    }

    private void loadAreas(){
        @SuppressLint("StaticFieldLeak")
        AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                String json = "";
                try {
                    Resources res = getResources();
                    String uri = res.getString(R.string.host) + res.getString(R.string.get_areas_uri);
                    json = JsonAPI.get(uri);
                    Log.e("data", json);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return json;
            }
        };
        task.execute();
        Gson gson = new Gson();
        try {
            AreasResponse result = gson.fromJson(task.get(), AreasResponse.class);
            areaList.addAll(result.entries);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void loadFloors(Area area){
        floorList.clear();
        @SuppressLint("StaticFieldLeak")
        AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                String json = "";
                try {
                    Resources res = getResources();
                    String uri = res.getString(R.string.host) +
                            res.getString(R.string.get_floors_uri) + String.valueOf(area.getId());
                    json = JsonAPI.get(uri);
                    Log.e("data", json);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return json;
            }
        };
        task.execute();
        Gson gson = new Gson();
        try {
            FloorsResponse result = gson.fromJson(task.get(), FloorsResponse.class);
            floorList.addAll(result.entries);
            for(Floor floor: floorList){
                floor.setArea(area);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void loadRooms(){
        final Gson gson = new Gson();
        //load data here
        @SuppressLint("StaticFieldLeak")
        AsyncTask<Void, String, String> task = new AsyncTask<Void, String, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                String json = "";
                try {
                    Resources res = getResources();
                    for(Floor floor: floorList) {
                        String uri = res.getString(R.string.host) +
                                res.getString(R.string.get_rooms_uri) +
                                String.valueOf(floor.getAreaId()) + "/" +
                                String.valueOf(floor.getId());
                        json = JsonAPI.get(uri);
                        Log.e("data", json);

                        publishProgress(json, String.valueOf(floorList.indexOf(floor)));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return json;
            }

            @Override
            protected void onProgressUpdate(String... values) {
                super.onProgressUpdate(values);
                String floorJson = values[0];
                Floor floor = floorList.get(Integer.valueOf(values[1]));

                RoomsResponse result = gson.fromJson(floorJson, RoomsResponse.class);
                List<Room> roomList = new ArrayList<>(result.entries);
                for(Room room: roomList){
                    room.setFloor(floor);
                }
                floor.setRooms(roomList);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                floorAdapter.notifyDataSetChanged();
            }
        };
        task.execute();
    }

    @Override
    public void onRoomClick(Room room) {
        startAnimation.onAnimationStart(room);
    }
}
