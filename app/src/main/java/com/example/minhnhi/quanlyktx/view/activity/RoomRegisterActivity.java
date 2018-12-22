package com.example.minhnhi.quanlyktx.view.activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;

import com.example.minhnhi.quanlyktx.R;
import com.example.minhnhi.quanlyktx.beans.RegisterResult;
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

import java.util.Date;
import java.util.List;

public class RoomRegisterActivity extends KtxActivity implements OnRegisterListener {
    private UserAccount account;
    public static TimeRegister timeRegister;

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
        RegisterResult registerResult = getCurrentRoomRegister();
        if(registerResult != null){
            RoomRegisterFragment fragment = new RoomRegisterFragment();
            fragment.setUserAccount(account);
            fragment.setRegisterResult(registerResult);
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
        RoomRegisterFragment fragment = new RoomRegisterFragment();
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

    public RegisterResult getCurrentRoomRegister(){
        String host = getResources().getString(R.string.host);
        String uri = getResources().getString(R.string.get_register_room_uri) + account.getId();
        BaseMsg<List<RegisterResult>> msg = new BaseMsg<>(host+uri, ApiMethod.GET, ApiResponseClass.RegisterResultResponse.class);
        msg.resolveDataOnMainThread();
        if(msg.getCode() == ErrorCode.SUCCESS){
            return msg.getData().get(0);
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
                    RoomRegisterActivity.timeRegister = timeRegister;
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
