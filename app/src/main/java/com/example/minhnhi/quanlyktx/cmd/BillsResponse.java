package com.example.minhnhi.quanlyktx.cmd;

import com.example.minhnhi.quanlyktx.beans.Bill;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BillsResponse extends BaseResponse {
    @SerializedName("data")
    public Bill entry;
}
