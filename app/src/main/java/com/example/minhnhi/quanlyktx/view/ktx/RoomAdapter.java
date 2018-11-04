package com.example.minhnhi.quanlyktx.view.ktx;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.minhnhi.quanlyktx.R;
import com.example.minhnhi.quanlyktx.beans.Room;

import java.util.ArrayList;
import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomHolder> {

    private List<Room> rooms;
    public interface OnRoomClickListener{
        void onRoomClick(Room room);
    }
    private OnRoomClickListener mOnItemClickListener;

    public RoomAdapter(List<Room> rooms){
        this.rooms = rooms;
    }

    @NonNull
    @Override
    public RoomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.room_item_layout, parent, false);
        return new RoomHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomHolder holder, int position) {
        Room room = rooms.get(position);
        holder.roomNumber.setText(room.getName());
        holder.usedNumber.setText(room.getUsedNumber());
        for(int i = 0; i < room.getStudentPresent(); i++){
            holder.icons.get(i).setImageResource(R.mipmap.men_icon_48);
        }
        for(int i = room.getStudentPresent(); i < room.getStudentMax(); i++){
            holder.icons.get(i).setImageResource(R.mipmap.men_icon_hide_48);
        }
    }

    @Override
    public int getItemCount() {
        return rooms.size();
    }

    public void setOnRoomClickListener(OnRoomClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    private void onItemHolderClick(RoomHolder itemHolder) {
        if (mOnItemClickListener != null) {
            Room room = rooms.get(itemHolder.getAdapterPosition());
            mOnItemClickListener.onRoomClick(room);
        }
    }


    class RoomHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView roomNumber, usedNumber;
        private RoomAdapter adapter;
        private List<ImageView> icons = new ArrayList<>();

        RoomHolder(View itemView, RoomAdapter adapter) {
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
