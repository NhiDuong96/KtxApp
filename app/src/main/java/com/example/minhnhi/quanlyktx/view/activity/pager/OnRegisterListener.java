package com.example.minhnhi.quanlyktx.view.activity.pager;

import com.example.minhnhi.quanlyktx.beans.RegisterRoom;

public interface OnRegisterListener {
    void onRegisterSuccess(RegisterRoom registerRoom);
    void onRegisterFailed();
}
