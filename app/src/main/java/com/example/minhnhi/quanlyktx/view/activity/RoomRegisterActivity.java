package com.example.minhnhi.quanlyktx.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.util.Log;

import com.example.minhnhi.quanlyktx.R;
import com.example.minhnhi.quanlyktx.beans.RegisterRoom;
import com.example.minhnhi.quanlyktx.beans.Room;
import com.example.minhnhi.quanlyktx.beans.UserAccount;
import com.example.minhnhi.quanlyktx.view.activity.pager.OnRegisterListener;
import com.example.minhnhi.quanlyktx.view.activity.pager.RoomRegisterPagerFactory;
import com.example.minhnhi.quanlyktx.view.ktx.KtxActivity;
import com.example.minhnhi.quanlyktx.view.ktx.RoomDetailsFragment;

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
}
