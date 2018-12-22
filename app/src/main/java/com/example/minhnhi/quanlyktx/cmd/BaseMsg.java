package com.example.minhnhi.quanlyktx.cmd;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import com.example.minhnhi.quanlyktx.utils.JsonAPI;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.concurrent.ExecutionException;

public class BaseMsg<Result> {

    private ApiResponse<Result> res;

    private String uri;

    private ApiMethod method;

    private ErrorCode errorCode ;

    private String jsonPost;

    private String jsonRes;

    private Gson gson;

    private Class<? extends ApiResponse> aClass;

    public BaseMsg(String uri, ApiMethod method, Class<? extends ApiResponse> aClass){
        this.uri = uri;
        this.method = method;
        this.aClass = aClass;
    }

    public BaseMsg(String uri, ApiMethod method){
        this.uri = uri;
        this.method = method;
    }

    public String getJsonPost() {
        return jsonPost;
    }

    public void setJsonPost(String jsonPost) {
        this.jsonPost = jsonPost;
    }

    public String getJsonRes() {
        return jsonRes;
    }

    public Gson getGsonParser() {
        if(gson == null){
            return new Gson();
        }
        return gson;
    }

    public void setGsonParser(Gson gson) {
        this.gson = gson;
    }

    @SuppressLint("StaticFieldLeak")
    public void exec(ApiLoadingObserver observer){
        new AsyncTask<Void, Void, String>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                if(observer != null){
                    observer.onLoadingStart();
                }
            }
            @Override
            protected String doInBackground(Void... voids) {
                try {
                    switch (method){
                        case GET:
                            jsonRes = JsonAPI.get(uri);
                            break;
                        case POST:
                            jsonRes = JsonAPI.postForResponse(getJsonPost(), uri, HttpURLConnection.HTTP_OK);
                            break;
                        default:
                            errorCode = ErrorCode.INVALID_API_METHOD;
                            break;
                    }
                    Log.e("data", jsonRes);
                    res = getGsonParser().fromJson(jsonRes, ApiResponse.class);
                    if(res == null || res.data == null || res.code != 200){
                        errorCode = ErrorCode.DATA_NULL;
                    }
                    else errorCode = ErrorCode.SUCCESS;
                }catch (IOException ex){
                    errorCode = ErrorCode.ERROR_API_LOADING;
                }
                return jsonRes;
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if(observer != null) {
                    if (errorCode == ErrorCode.SUCCESS) {
                        observer.onLoadingSuccess(s);
                    }
                    else if(res != null){
                        observer.onLoadingFailed(errorCode, res.message);
                    }
                    observer.onLoadingFinish();
                }
            }
        }.execute();
    }


    @SuppressLint("StaticFieldLeak")
    public void resolveDataOnMainThread(){
        AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
            String json = "";
            @Override
            protected String doInBackground(Void... voids) {
                try {
                    switch (method){
                        case GET:
                            json = JsonAPI.get(uri);
                            break;
                        case POST:
                            json = JsonAPI.postForResponse(getJsonPost(), uri, HttpURLConnection.HTTP_OK);
                            break;
                        default:
                            errorCode = ErrorCode.INVALID_API_METHOD;
                            break;
                    }
                    Log.e("data", json);

                }catch (IOException ex){
                    errorCode = ErrorCode.ERROR_API_LOADING;
                }
                return json;
            }
        };
        task.execute();
        if(aClass == null) return;
        try {
            jsonRes = task.get();
            res = getGsonParser().fromJson(jsonRes, aClass);
            if(res == null || res.data == null || res.code != 200){
                errorCode = ErrorCode.DATA_NULL;
            }
            else errorCode = ErrorCode.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            errorCode = ErrorCode.ERROR_TASK_GET;
        }
    }


    public ErrorCode getCode(){
        if(errorCode == null){
            return ErrorCode.FAILED;
        }
        return errorCode;
    }

    public String getMessge(){
        if(res != null){
            return res.message;
        }
        return "No Message";
    }
    public Result getData(){
        return res.data;
    }
}
