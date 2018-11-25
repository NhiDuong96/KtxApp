package com.example.minhnhi.quanlyktx.view.ktx;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.minhnhi.quanlyktx.R;
import com.example.minhnhi.quanlyktx.beans.Floor;
import com.example.minhnhi.quanlyktx.beans.Room;

import java.util.List;

public class FloorAdapter extends RecyclerView.Adapter<FloorAdapter.FloorHolder>{
    private List<Floor> floors;
    private Context context;

    private RoomAdapter.OnRoomClickListener listener;

    public FloorAdapter(Context context, List<Floor> floors){
        this.context = context;
        this.floors = floors;
    }

    public void setOnRoomClickListener(RoomAdapter.OnRoomClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public FloorHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.floor_item_layout, viewGroup, false);
        return new FloorHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FloorHolder floorHolder, int i) {
        List<Room> rooms = floors.get(i).getRooms();
        RoomAdapter adapter = getRoomAdapter(rooms);
        floorHolder.floorName.setText(floors.get(i).getName());
        floorHolder.recyclerView.setHasFixedSize(true);
        floorHolder.recyclerView.setLayoutManager(
                new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        floorHolder.recyclerView.setAdapter(adapter);
        adapter.setOnRoomClickListener(listener);
    }

    public RoomAdapter getRoomAdapter(List<Room> rooms){
        return new RoomAdapter(rooms);
    }

    @Override
    public int getItemCount() {
        return this.floors.size();
    }

    class FloorHolder extends RecyclerView.ViewHolder{
        TextView floorName;
        RecyclerView recyclerView;
        FloorHolder(View view){
            super(view);
            recyclerView = view.findViewById(R.id.roomList);
            floorName = view.findViewById(R.id.floorName);
        }
    }
}
