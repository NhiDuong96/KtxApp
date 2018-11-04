package com.example.minhnhi.quanlyktx.cmd;

import com.example.minhnhi.quanlyktx.beans.Floor;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FloorsResponse {
    @SerializedName("data")
    public List<Floor> entries;
}
