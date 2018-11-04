package com.example.minhnhi.quanlyktx.cmd;

import com.example.minhnhi.quanlyktx.beans.Room;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RoomsResponse extends BaseResponse {
    @SerializedName("data")
    public List<Room> entries;
}
