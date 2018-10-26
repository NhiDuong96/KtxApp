package com.example.minhnhi.quanlyktx.home;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.List;

public abstract class HomePage<T> {
    private List<T> data;
    void initData(String json){
        data = parseJson(json);
    }
    public List<T> getData(){
        return data;
    }
    protected abstract String getAPI();
    protected abstract List<T> parseJson(String json);
    public abstract RecyclerView.Adapter getAdapter();
}
