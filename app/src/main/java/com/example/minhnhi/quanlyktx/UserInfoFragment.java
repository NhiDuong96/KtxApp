package com.example.minhnhi.quanlyktx;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.minhnhi.quanlyktx.beans.Notification;
import com.example.minhnhi.quanlyktx.beans.UserProfile;
import com.example.minhnhi.quanlyktx.utils.InputView;
import com.example.minhnhi.quanlyktx.utils.JsonAPI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class UserInfoFragment extends Fragment implements View.OnClickListener {
    private List<Notification> notifications;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_info_fragment, container, false);

        Button b = view.findViewById(R.id.notification);
        b.setText(getUserNotification());
        b.setOnClickListener(this);

        Intent intent = getActivity().getIntent();
        String userName = intent.getStringExtra("user");
        UserProfile profile = getUserProfile(userName);
        InputView v = view.findViewById(R.id.mssv);
        v.setText(profile.getMssv());
        v = view.findViewById(R.id.name);
        v.setText(profile.getName());
        v = view.findViewById(R.id.gentle);
        v.setText(profile.getGentle());
        v = view.findViewById(R.id.year);
        v.setText(profile.getYear());
        v = view.findViewById(R.id.birthday);
        v.setText(profile.getBirthday());
        v = view.findViewById(R.id.cmnd);
        v.setText(profile.getCmnd());
        return view;
    }


    @SuppressLint("StaticFieldLeak")
    public UserProfile getUserProfile(String userName){
        UserProfile profile = null;
        try {
            profile = new AsyncTask<String, Void, UserProfile>() {
                @Override
                protected UserProfile doInBackground(String... strings) {
                    String name = strings[0];
                    String json = "";
                    try {
                        json = JsonAPI.get(getResources().getString(R.string.user_info_uri)); //+name
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return UserProfile.parseJson(json);
                }
            }.execute(userName).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return profile;
    }

    public String getUserNotification(){
        int num = 56;
        //load notification
        notifications = new ArrayList<>();


        return "Hiện tại bạn đang có "+ num +" thông báo chưa đọc.";
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.notification:
                getFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, new UserNotificationFragment())
                        .commit();
                break;
        }
    }
}
