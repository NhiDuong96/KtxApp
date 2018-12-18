package com.example.minhnhi.quanlyktx.cmd;

import com.google.gson.annotations.SerializedName;

public class ApiResponse<T> {
    @SerializedName("code")
    public int code;
    @SerializedName("message")
    public String message;
    @SerializedName("data")
    T data;

    public T getData(){
        return data;
    }
}
