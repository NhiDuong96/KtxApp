package com.example.minhnhi.quanlyktx.view.home.page;

import com.example.minhnhi.quanlyktx.R;
import com.example.minhnhi.quanlyktx.beans.Notification;
import com.example.minhnhi.quanlyktx.cmd.NotificationsResponse;
import com.example.minhnhi.quanlyktx.utils.GsonDateFormatter;
import com.example.minhnhi.quanlyktx.view.home.HomePage;
import com.example.minhnhi.quanlyktx.view.home.NotificationAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NotificationPage extends HomePage {
    @Override
    public int getAPI() {
        return R.string.get_home_notifications_uri;
    }

    @Override
    protected int getTitleId() {
        return R.string.home_title;
    }

    @Override
    public List<Notification> parseJson(String json) {
        if(json == null || json.isEmpty()){
            List<Notification> notifications = new ArrayList<>();
            notifications.add(new Notification(1,"Thông báo về việc đăng ký hè năm 2018", new Date(), "no content"));
            notifications.add(new Notification(2,"Thông báo về việc trả phòng năm học 2017-2018", new Date(), "no content"));
            notifications.add(new Notification(3,"Thông báo về việc đăng ký nội trú đợt 1", new Date(), "no content"));
            return notifications;
        }
        //parse json here
        NotificationsResponse res = GsonDateFormatter.getGson().fromJson(json, NotificationsResponse.class);
        return res.entries;
    }
}
