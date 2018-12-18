package com.example.minhnhi.quanlyktx.view.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.minhnhi.quanlyktx.R;
import com.example.minhnhi.quanlyktx.cmd.ApiLoadingObserverAdapter;
import com.example.minhnhi.quanlyktx.cmd.ApiMethod;
import com.example.minhnhi.quanlyktx.cmd.BaseMsg;
import com.example.minhnhi.quanlyktx.cmd.ErrorCode;
import com.example.minhnhi.quanlyktx.utils.JsonAPI;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class HomePageMgr {
    private HashMap<Integer, HomePage> cachePage;
    private HomePageFactory factory;
    private static HomePageMgr instance;

    private HomePageMgr(){
        cachePage = new HashMap<>();
    }

    public static HomePageMgr getInstance(){
        if(instance == null){
            instance = new HomePageMgr();
        }
        return instance;
    }

    public void setPageFactory(HomePageFactory factory){
        this.factory = factory;
    }

    public HomePageFactory getPageFactory(){
        return factory;
    }

    public HomePage getPage(int pageId, Context context){
        if(!cachePage.containsKey(pageId)){
            if(!loadPage(pageId, context))
                return factory.createPage(pageId);
        }
        return cachePage.get(pageId);
    }

    public void removePageCache(int pageId){
        cachePage.remove(pageId);
    }

    public void removeAllPageCache(){
        cachePage.clear();
    }

    @SuppressLint("StaticFieldLeak")
    private boolean loadPage(final int pageId, Context context){
        Log.e("load page", String.valueOf(pageId));
        if(factory == null) return false;
        HomePage page = factory.createPage(pageId);
        String host = context.getResources().getString(R.string.host1);
        String uri = context.getResources().getString(page.getAPI());
        BaseMsg<Void> msg = new BaseMsg<>(host+uri, ApiMethod.GET);
        msg.resolveDataOnMainThread();
        page.initData(msg.getJsonRes());
        cachePage.put(pageId, page);
        return true;
    }
}
