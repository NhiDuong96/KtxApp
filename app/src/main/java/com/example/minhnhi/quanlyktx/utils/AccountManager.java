package com.example.minhnhi.quanlyktx.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.example.minhnhi.quanlyktx.R;
import com.example.minhnhi.quanlyktx.beans.UserAccount;

import org.json.JSONException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.concurrent.ExecutionException;

public class AccountManager implements Serializable{
    private static final String TAG = "AccountManager";

    public interface OnLoginSuccessListener{
        void onLogged(UserAccount account);
    }

    private OnLoginSuccessListener listener;

    private Context context;

    private boolean isLogin = false;

    private static AccountManager accountManager;

    private Handler handler = new Handler( new Handler.Callback(){

        @Override
        public boolean handleMessage(Message message) {
            if(message.what == 0)
                Toast.makeText(context, "Thông tin đăng nhập không đúng!", Toast.LENGTH_SHORT).show();
            return false;
        }
    });

    private AccountManager(){ }

    public static AccountManager getAccountManager() {
        if(accountManager == null){
            accountManager = new AccountManager();
        }
        return accountManager;
    }

    @SuppressLint("StaticFieldLeak")
    public void logIn(final Context context, final UserAccount account, boolean remember, OnLoginSuccessListener listener){
        this.context = context;
        try {
            isLogin = new AsyncTask<Void, Void, Boolean>() {
                @Override
                protected Boolean doInBackground(Void... voids) {
                    try {

                        JsonAPI.post(account.toJson().toString(), account.getApiUrl());

                    } catch (IOException | JSONException e) {
                        Message msg = new Message();
                        msg.what = 0;
                        handler.sendMessage(msg);
                        return false;
                    }
                    return true;
                }
            }.execute().get();
            if(isLogin && listener != null){
                listener.onLogged(account);
            }
        } catch (InterruptedException | ExecutionException e) {
            Toast.makeText(context, "Xảy ra lỗi, đăng nhập thất bại!", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            return;
        }
        //save data to file
        if(remember) saveAccountToInternalStorage(context,account);
    }

    public void logOut(Context context){
        deleteAccountFromInternalStorage(context);
        isLogin = false;
    }

    public boolean isLogged(){
        return isLogin;
    }

    public UserAccount getLoggedAccount(Context context){
        return readAccountFromInternalStorage(context);
    }

    private boolean saveAccountToInternalStorage(Context context, UserAccount account){
        try {
            FileOutputStream fos = context.openFileOutput("account.dat", Context.MODE_PRIVATE);
            ObjectOutputStream o = new ObjectOutputStream(fos);

            o.writeObject(account);
            o.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private static UserAccount readAccountFromInternalStorage(Context context){

        try {
            File file = context.getFileStreamPath("account.dat");
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream o = new ObjectInputStream(fis);

            return (UserAccount) o.readObject();
        } catch (IOException | ClassNotFoundException e) {
            Log.e(TAG, "No loged account");
        }
        return null;
    }

    private void deleteAccountFromInternalStorage(Context context){
        context.deleteFile("account.bat");
    }
}
