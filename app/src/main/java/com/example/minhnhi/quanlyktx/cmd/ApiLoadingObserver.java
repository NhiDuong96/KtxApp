package com.example.minhnhi.quanlyktx.cmd;

public interface ApiLoadingObserver {
    void onLoadingStart();

    void onLoadingSuccess(String json);

    void onLoadingFailed(ErrorCode code, String msg);

    void onLoadingFinish();
}
