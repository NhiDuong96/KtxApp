package com.example.minhnhi.quanlyktx.cmd;

import com.example.minhnhi.quanlyktx.beans.Area;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AreasResponse extends BaseResponse{
    @SerializedName("data")
    public List<Area> entries;
}
