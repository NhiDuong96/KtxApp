package com.example.minhnhi.quanlyktx.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;

import com.example.minhnhi.quanlyktx.R;
import com.example.minhnhi.quanlyktx.beans.RegisterRoom;
import com.example.minhnhi.quanlyktx.beans.Room;
import com.example.minhnhi.quanlyktx.beans.TimeRegister;
import com.example.minhnhi.quanlyktx.beans.UserAccount;
import com.example.minhnhi.quanlyktx.cmd.ApiMethod;
import com.example.minhnhi.quanlyktx.cmd.ApiResponseClass;
import com.example.minhnhi.quanlyktx.cmd.BaseMsg;
import com.example.minhnhi.quanlyktx.cmd.ErrorCode;
import com.example.minhnhi.quanlyktx.utils.GsonDateFormatter;
import com.example.minhnhi.quanlyktx.view.activity.pager.OnRegisterListener;
import com.example.minhnhi.quanlyktx.view.activity.pager.RoomRegisterPagerFactory;
import com.example.minhnhi.quanlyktx.view.ktx.KtxActivity;
import com.example.minhnhi.quanlyktx.view.ktx.RoomDetailsFragment;

import java.sql.Time;
import java.util.Date;
import java.util.List;

public class RoomRegisterActivity extends KtxActivity implements OnRegisterListener {
    private UserAccount account;

    @Override
    protected void onCreate(){
        setContentView(R.layout.activity_ktx);

        Intent intent = getIntent();
        account = (UserAccount) intent.getSerializableExtra("account");

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.activity_title);
            actionBar.setHomeAsUpIndicator(R.mipmap.home_icon);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //load if in time register
        if(!isInTimeRegister()){
            View view = findViewById(R.id.time_register);
            view.setVisibility(View.VISIBLE);
            view.findViewById(R.id.back).setOnClickListener(v -> {
                RoomRegisterActivity.this.finish();
            });
            return;
        }

        //load if user already register
        RegisterRoom registerRoom = getCurrentRoomRegister();
        if(registerRoom != null){
            RoomRegisterResultFragment fragment = new RoomRegisterResultFragment();
            fragment.setUserAccount(account);
            fragment.setRegisterRoom(registerRoom);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fragment)
                    .commit();
            return;
        }

        //
        KtxRegisterInfoFragment ktxInfoFragment = new KtxRegisterInfoFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, ktxInfoFragment)
                .commit();
        ktxInfoFragment.setClickListener(this);
    }

    public RoomDetailsFragment getRoomDetailsFragment(Room room){
        RoomRegisterDetailsFragment roomDetailsFragment = new RoomRegisterDetailsFragment();
        roomDetailsFragment.setRoom(room);
        roomDetailsFragment.setPagerFactory(new RoomRegisterPagerFactory());
        roomDetailsFragment.setOnSlideFragmentAnimationEnd(this);
        roomDetailsFragment.setOnRegisterListener(this);
        return roomDetailsFragment;
    }

    @Override
    public void onRegisterSuccess(RegisterRoom registerRoom) {
        RoomRegisterResultFragment fragment = new RoomRegisterResultFragment();
        fragment.setUserAccount(account);
        fragment.setRegisterRoom(registerRoom);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    @Override
    public void onRegisterFailed() {

    }

    public RegisterRoom getCurrentRoomRegister(){
        String host = getResources().getString(R.string.host);
        String uri = "";
        BaseMsg<RegisterRoom> msg = new BaseMsg<>(host+uri, ApiMethod.GET, ApiResponseClass.RegisterRoomResponse.class);
        msg.resolveDataOnMainThread();
        if(msg.getCode() == ErrorCode.SUCCESS){
            return msg.getData();
        }
        return null;
    }

    public boolean isInTimeRegister(){
        String host = getResources().getString(R.string.host);
        String uri = getResources().getString(R.string.time_register_uri);
        BaseMsg<List<TimeRegister>> msg = new BaseMsg<>(host+uri, ApiMethod.GET, ApiResponseClass.TimeRegisterResponse.class);
        msg.setGsonParser(GsonDateFormatter.getGson2());
        msg.resolveDataOnMainThread();
        if(msg.getCode() == ErrorCode.SUCCESS){
            Date now = new Date();
            for(TimeRegister timeRegister: msg.getData()){
                if(now.compareTo(timeRegister.getDateBegin()) >= 0 &&
                        now.compareTo(timeRegister.getDateEnd()) <= 0){
                    return true;
                }
                else{
                    Log.e("date", timeRegister.getDateBegin().toGMTString());
                    Log.e("date", timeRegister.getDateEnd().toGMTString());
                }
            }

        }
        return false;
    }
}
