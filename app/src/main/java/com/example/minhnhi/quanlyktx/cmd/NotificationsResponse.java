package com.example.minhnhi.quanlyktx.cmd;

import com.example.minhnhi.quanlyktx.beans.Notification;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NotificationsResponse extends BaseResponse {
    @SerializedName("data")
    public List<Notification> entries;
}
