package com.example.minhnhi.quanlyktx.view.activity;

import android.content.Context;

import com.example.minhnhi.quanlyktx.beans.Floor;
import com.example.minhnhi.quanlyktx.view.ktx.FloorAdapter;
import com.example.minhnhi.quanlyktx.view.ktx.KtxInfoFragment;

import java.util.List;

public class KtxRegisterInfoFragment extends KtxInfoFragment{

    public FloorAdapter getFloorAdapter(Context context, List<Floor> floors){
        return new FloorRegisterAdapter(context, floors);
    }
}
