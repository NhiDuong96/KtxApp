package com.example.minhnhi.quanlyktx.cmd;

import com.google.gson.annotations.SerializedName;

public class BaseResponse {
    @SerializedName("code")
    public int code;
    @SerializedName("message")
    public String message;
}
