package com.example.minhnhi.quanlyktx.view.home;

import com.example.minhnhi.quanlyktx.beans.Notification;

import java.util.ArrayList;
import java.util.List;

public abstract class HomePage {
    private List<Notification> data;
    void initData(String json){
        data = parseJson(json);
    }
    protected List<Notification> parseJson(String json){
        //parse Json here
        return new ArrayList<>();
    }
    public List<Notification> getData(){
        return data;
    }
    public NotificationAdapter getAdapter() {
        return new NotificationAdapter(getData());
    }
    public Notification getItem(int position){
        return data.get(position);
    }

    protected abstract String getAPI();
}
