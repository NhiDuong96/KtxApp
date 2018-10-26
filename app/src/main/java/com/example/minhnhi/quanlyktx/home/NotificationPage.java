package com.example.minhnhi.quanlyktx.home;

import android.support.v7.widget.RecyclerView;

import com.example.minhnhi.quanlyktx.NotificationAdapter;
import com.example.minhnhi.quanlyktx.beans.Notification;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NotificationPage extends HomePage<Notification> {
    @Override
    public String getAPI() {
        return "";
    }

    @Override
    public List<Notification> parseJson(String json) {
        if(json == null || json.isEmpty()){
            List<Notification> notifications = new ArrayList<>();
            notifications.add(new Notification("kasdnafks", new Date(), "no content"));
            notifications.add(new Notification("kasdnafks", new Date(), "no content"));
            notifications.add(new Notification("kasdnafks", new Date(), "no content"));
            return notifications;
        }
        //parse json here

        return null;
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        return new NotificationAdapter(getData());
    }
}
