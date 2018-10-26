package com.example.minhnhi.quanlyktx;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.minhnhi.quanlyktx.beans.Room;
import com.example.minhnhi.quanlyktx.utils.OnSlideAnimationStartListener;

import java.util.ArrayList;
import java.util.List;

public class BuildingInfoFragment extends Fragment implements AdapterView.OnItemClickListener {
    private RecyclerView recyclerView;
    private BuildingInfoAdapter adapter;
    private List<Room> roomList = new ArrayList<>();

    private OnSlideAnimationStartListener startAnimation;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.building_info_fragment, container, false);

        for(int i = 0; i < 9; i++){
            roomList.add(new Room(101,i,8,1));
        }

        recyclerView = view.findViewById(R.id.cardList);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        adapter = new BuildingInfoAdapter(roomList);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);
        return view;
    }

    public void setClickListener(OnSlideAnimationStartListener startAnimation) {
        this.startAnimation = startAnimation;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(getContext(), String.valueOf(i), Toast.LENGTH_SHORT).show();
        startAnimation.onAnimationStart();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        }
    }
}
