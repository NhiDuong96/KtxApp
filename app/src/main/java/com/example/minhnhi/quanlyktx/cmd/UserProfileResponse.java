package com.example.minhnhi.quanlyktx.cmd;

import com.example.minhnhi.quanlyktx.beans.UserProfile;
import com.google.gson.annotations.SerializedName;

public class UserProfileResponse extends BaseResponse {
    @SerializedName("data")
    public UserProfile entry;
}
