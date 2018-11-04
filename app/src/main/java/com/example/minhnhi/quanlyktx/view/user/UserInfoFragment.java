package com.example.minhnhi.quanlyktx.view.user;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.minhnhi.quanlyktx.R;
import com.example.minhnhi.quanlyktx.beans.Notification;
import com.example.minhnhi.quanlyktx.beans.UserAccount;
import com.example.minhnhi.quanlyktx.beans.UserProfile;
import com.example.minhnhi.quanlyktx.cmd.NotificationsResponse;
import com.example.minhnhi.quanlyktx.utils.GsonDateFormatter;
import com.example.minhnhi.quanlyktx.utils.InputView;
import com.example.minhnhi.quanlyktx.utils.JsonAPI;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class UserInfoFragment extends Fragment implements View.OnClickListener {
    private List<Notification> notifications;
    private UserAccount account;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_info_fragment, container, false);

        Button b = view.findViewById(R.id.notification);
        b.setText(getUserNotification());
        b.setOnClickListener(this);

        UserProfile profile = loadUserProfile();
        if(profile == null) return view;
        InputView v = view.findViewById(R.id.mssv);
        v.setText(profile.getMssv());
        v = view.findViewById(R.id.name);
        v.setText(profile.getName());
        v = view.findViewById(R.id.gender);
        v.setText(profile.getGender());
        v = view.findViewById(R.id.year);
        v.setText(profile.getYear());
        v = view.findViewById(R.id.birthday);
        v.setText(profile.getBirthday());
        v = view.findViewById(R.id.cmnd);
        v.setText(profile.getCmnd());
        v = view.findViewById(R.id.className);
        v.setText(profile.getClassName());
        v = view.findViewById(R.id.phone);
        v.setText(profile.getPhone());
        v = view.findViewById(R.id.address);
        v.setText(profile.getAddress());
        return view;
    }

    public void setUserAccount(UserAccount account){
        this.account = account;
    }

    public UserProfile loadUserProfile(){
        UserProfile profile = null;
        Resources res = getResources();
        @SuppressLint("StaticFieldLeak")
        AsyncTask<Void, Void, UserProfile> task = new AsyncTask<Void, Void, UserProfile>() {
            @Override
            protected UserProfile doInBackground(Void... voids) {
                String json;
                try {
                    json = JsonAPI.get(res.getString(R.string.host)
                            + res.getString(R.string.user_info_uri) +
                            + account.getId());
                    Log.e("data", json);
                    return new Gson().fromJson(json, UserProfile.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        task.execute();
        try {
            profile = task.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return profile;
    }

    public String getUserNotification(){
        notifications = new ArrayList<>();

        Resources res = getResources();
        @SuppressLint("StaticFieldLeak")
        AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                String json = "";
                try {
                    json = JsonAPI.get(res.getString(R.string.host)
                            + res.getString(R.string.user_notification_uri)
                            + account.getId());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return json;
            }
        };
        task.execute();
        Gson gson = GsonDateFormatter.getGson();
        try {
            notifications = gson.fromJson(task.get(), NotificationsResponse.class).entries;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return "Hiện tại bạn đang có "+ notifications.size() +" thông báo chưa đọc.";
    }

    public List<Notification> getUserNotifications(){
        if(notifications == null){

            notifications = new ArrayList<>();
        }
        return notifications;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.notification:
                UserNotificationFragment userNotificationFragment = new UserNotificationFragment();
                userNotificationFragment.setNotifications(notifications);
                getFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, userNotificationFragment)
                        .commit();
                break;
        }
    }
}
