package com.example.minhnhi.quanlyktx.view.activity;

import com.example.minhnhi.quanlyktx.beans.RegisterRoom;
import com.example.minhnhi.quanlyktx.view.activity.pager.OnRegisterListener;
import com.example.minhnhi.quanlyktx.view.ktx.RoomDetailsFragment;

public class RoomRegisterDetailsFragment extends RoomDetailsFragment {
    private OnRegisterListener listener;
    public void setOnRegisterListener(OnRegisterListener listener){
        this.listener = listener;
    }

    public void onRegisterSuccess(RegisterRoom registerRoom){
        listener.onRegisterSuccess(registerRoom);
    }


}
