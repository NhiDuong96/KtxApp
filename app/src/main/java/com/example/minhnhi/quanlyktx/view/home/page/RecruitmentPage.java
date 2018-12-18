package com.example.minhnhi.quanlyktx.view.home.page;

import com.example.minhnhi.quanlyktx.R;
import com.example.minhnhi.quanlyktx.beans.Notification;
import com.example.minhnhi.quanlyktx.cmd.ApiResponse;
import com.example.minhnhi.quanlyktx.cmd.ApiResponseClass;
import com.example.minhnhi.quanlyktx.utils.GsonDateFormatter;
import com.example.minhnhi.quanlyktx.view.home.HomePage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RecruitmentPage extends HomePage {
    @Override
    public int getAPI() {
        return R.string.get_home_notifications_uri;
    }

    @Override
    protected int getTitleId() {
        return R.string.home_title_2;
    }

    @Override
    public List<Notification> parseJson(String json) {
        if(json == null || json.isEmpty()){
            List<Notification> notifications = new ArrayList<>();
            notifications.add(new Notification(1,"Hiện tại không có thông tin tuyển dụng nào!", new Date(), "No recruitment information available!"));
            return notifications;
        }
        //parse json here
        ApiResponse<List<Notification>> res = GsonDateFormatter.getGson().fromJson(json, ApiResponseClass.NotificationResponse.class);
        return res.getData();
    }
}
