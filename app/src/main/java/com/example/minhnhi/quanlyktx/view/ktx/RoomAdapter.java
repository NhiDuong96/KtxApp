package com.example.minhnhi.quanlyktx.view.ktx;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.minhnhi.quanlyktx.R;
import com.example.minhnhi.quanlyktx.beans.Room;
import com.example.minhnhi.quanlyktx.view.ktx.helper.RoomFunctionIconFactory;

import java.util.ArrayList;
import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomHolder> {

    protected List<Room> rooms;
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
        holder.usedNumber.setText(getUsedNumber(room));
        int functionIcon = RoomFunctionIconFactory.getIcon(room.getFunctionId());
        if(functionIcon == -1) {
            for (int i = 0; i < getCurrentStudent(room); i++) {
                holder.icons.get(i).setImageResource((room.getGender() == 1) ? R.mipmap.men_icon_48 : R.mipmap.woman_icon_48);
            }
            for (int i = getCurrentStudent(room); i < room.getStudentMax(); i++) {
                holder.icons.get(i).setImageResource((room.getGender() == 1) ? R.mipmap.men_icon_hide_48 : R.mipmap.woman_icon_hide_48);
            }
        }else{
            for (int i = 0; i < 8; i++) {
                holder.icons.get(i).setVisibility(View.GONE);
            }
            holder.functionImg.setImageResource(functionIcon);
            holder.functionImg.setVisibility(View.VISIBLE);
        }
    }

    public int getCurrentStudent(Room room){
        return room.getStudentPresent();
    }

    public String getUsedNumber(Room room){
        return String.valueOf(room.getStudentPresent()) + "/" + String.valueOf(room.getStudentMax());
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
            if(room.getFunctionId() == -1) {
                mOnItemClickListener.onRoomClick(room);
            }
        }
    }


    class RoomHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView roomNumber;
        TextView usedNumber;
        RoomAdapter adapter;
        List<ImageView> icons = new ArrayList<>();
        ImageView functionImg;

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

            functionImg = itemView.findViewById(R.id.function);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            adapter.onItemHolderClick(this);
        }
    }
}
