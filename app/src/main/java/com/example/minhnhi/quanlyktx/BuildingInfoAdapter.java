package com.example.minhnhi.quanlyktx;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.minhnhi.quanlyktx.beans.Room;

import java.util.ArrayList;
import java.util.List;

public class BuildingInfoAdapter extends RecyclerView.Adapter<BuildingInfoAdapter.GridViewHolder> {

    private List<Room> rooms;
    private AdapterView.OnItemClickListener mOnItemClickListener;

    public BuildingInfoAdapter(List<Room> rooms){
        this.rooms = rooms;
    }

    @NonNull
    @Override
    public GridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.building_item_layout, parent, false);
        return new GridViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull GridViewHolder holder, int position) {
        Room room = rooms.get(position);
        holder.roomNumber.setText(String.valueOf(room.getRoomNumber()));
        holder.usedNumber.setText(room.toNumberUsed());
        for(int i = 0; i < room.getCurrentUsers(); i++){
            holder.icons.get(i).setImageResource(R.mipmap.men_icon_48);
        }
        for(int i = room.getCurrentUsers(); i < room.getMaxUsers(); i++){
            holder.icons.get(i).setImageResource(R.mipmap.men_icon_hide_48);
        }
    }

    @Override
    public int getItemCount() {
        return rooms.size();
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    private void onItemHolderClick(GridViewHolder itemHolder) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(null, itemHolder.itemView,
                    itemHolder.getAdapterPosition(), itemHolder.getItemId());
        }
    }


    class GridViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView roomNumber, usedNumber;
        private BuildingInfoAdapter adapter;
        private List<ImageView> icons = new ArrayList<>();

        GridViewHolder(View itemView, BuildingInfoAdapter adapter) {
            super(itemView);
            this.adapter = adapter;
            roomNumber = itemView.findViewById(R.id.roomNumber);
            usedNumber = itemView.findViewById(R.id.usedNumber);
            icons.add(itemView.findViewById(R.id.ic_1));
            icons.add(itemView.findViewById(R.id.ic_2));
            icons.add(itemView.findViewById(R.id.ic_3));
            icons.add(itemView.findViewById(R.id.ic_4));
            icons.add(itemView.findViewById(R.id.ic_5));
            icons.add(itemView.findViewById(R.id.ic_6));
            icons.add(itemView.findViewById(R.id.ic_7));
            icons.add(itemView.findViewById(R.id.ic_8));
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            adapter.onItemHolderClick(this);
        }
    }
}
