package com.example.minhnhi.quanlyktx.cmd;

import com.example.minhnhi.quanlyktx.beans.UserProfile;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StudentResponse extends BaseResponse {
    @SerializedName("data")
    public List<UserProfile> entries;
}
