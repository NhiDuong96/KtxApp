package com.example.minhnhi.quanlyktx.view.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.minhnhi.quanlyktx.R;
import com.example.minhnhi.quanlyktx.utils.JsonAPI;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class HomePageMgr {
    private HashMap<Integer, HomePage> cachePage;
    private HomePageFactory factory;
    private Context context;

    public HomePageMgr(Context context){
        cachePage = new HashMap<>();
        this.context = context;
    }

    public void setPageFactory(HomePageFactory factory){
        this.factory = factory;
    }

    public HomePageFactory getPageFactory(){
        return factory;
    }

    public HomePage getPage(int pageId){
        if(!cachePage.containsKey(pageId)){
            if(!loadPage(pageId)) return null;
        }
        return cachePage.get(pageId);
    }

    public void removePage(int pageId){
        cachePage.remove(pageId);
    }

    @SuppressLint("StaticFieldLeak")
    private boolean loadPage(final int pageId){
        Log.e("load page", String.valueOf(pageId));
        if(this.factory != null){
            final HomePage page = factory.createPage(pageId);
            AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
                @Override
                protected String doInBackground(Void... voids) {
                    String json = "";
                    String host = context.getResources().getString(R.string.host1);
                    String uri = context.getResources().getString(page.getAPI());
                    try {
                         json = JsonAPI.get(host+uri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return json;
                }
            };
            task.execute();
            try {
                page.initData(task.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            cachePage.put(pageId, page);
            return true;
        }
        return false;
    }
}
