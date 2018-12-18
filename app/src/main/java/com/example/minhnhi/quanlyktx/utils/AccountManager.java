package com.example.minhnhi.quanlyktx.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.example.minhnhi.quanlyktx.beans.UserAccount;
import com.example.minhnhi.quanlyktx.beans.AccountRegister;
import com.example.minhnhi.quanlyktx.beans.AccountLogin;
import com.example.minhnhi.quanlyktx.cmd.ApiLoadingObserverAdapter;
import com.example.minhnhi.quanlyktx.cmd.ApiMethod;
import com.example.minhnhi.quanlyktx.cmd.BaseMsg;
import com.example.minhnhi.quanlyktx.cmd.ErrorCode;
import com.google.gson.Gson;

import org.json.JSONException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class AccountManager implements Serializable{
    private static final String TAG = "AccountManager";

    public interface OnLoginSuccessListener{
        void onLoggedSuccess(UserAccount account);
        void onLoggedFailed(String msg);
    }

    private OnLoginSuccessListener listener;

    private Context context;

    private boolean isLogged = false;

    private UserAccount account;

    private static AccountManager accountManager;

    private AccountManager(){ }

    public static AccountManager getAccountManager() {
        if(accountManager == null){
            accountManager = new AccountManager();
        }
        return accountManager;
    }

    public void setListener(OnLoginSuccessListener listener) {
        this.listener = listener;
    }


    @SuppressLint("StaticFieldLeak")
    public void logIn(final Context context, String mssv, String password, String uri, boolean remember){
        this.context = context;
        AccountLogin req = new AccountLogin(mssv, "", password);

        BaseMsg<Void> msg = new BaseMsg<>(uri, ApiMethod.POST);
        msg.setJsonPost(new Gson().toJson(req));
        msg.exec(new ApiLoadingObserverAdapter(){
            @Override
            public void onLoadingSuccess(String json) {
                isLogged = true;
                try {
                    account = UserAccount.parseJson(json);
                    if(listener != null)
                        listener.onLoggedSuccess(account);
                    if(remember){
                        saveAccountToInternalStorage(account);
                    }
                } catch (JSONException e) {
                    isLogged = false;
                    if(listener != null)
                        listener.onLoggedFailed("Data error!");
                }
            }

            @Override
            public void onLoadingFailed(ErrorCode code, String msg) {
                isLogged = false;
                if(listener != null)
                    listener.onLoggedFailed(msg);
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    public void signIn(AccountRegister req, String uri){
        BaseMsg<Void> msg = new BaseMsg<>(uri, ApiMethod.POST);
        msg.setJsonPost(new Gson().toJson(req));
        msg.exec(new ApiLoadingObserverAdapter(){
            @Override
            public void onLoadingSuccess(String json) {
                isLogged = true;
                try {
                    account = UserAccount.parseJson(json);
                    if(listener != null)
                        listener.onLoggedSuccess(account);
                } catch (JSONException e) {
                    isLogged = false;
                    if(listener != null)
                        listener.onLoggedFailed("Data error!");
                }
            }

            @Override
            public void onLoadingFailed(ErrorCode code, String msg) {
                isLogged = false;
                if(listener != null)
                    listener.onLoggedFailed(msg);
            }
        });
    }

    public void logOut(Context context){
        deleteAccountFromInternalStorage(context);
        isLogged = false;
    }

    public boolean isLogged(Context context){
        if(!isLogged){
            account = readAccountFromInternalStorage(context);
            if(account != null) {
                return isLogged = true;
            }
            else return false;
        }
        return true;
    }

    public UserAccount getLoggedAccount(Context context){
        this.context = context;
        if(account == null){
            account = readAccountFromInternalStorage(context);
        }
        return account;
    }

    public void saveAccountToInternalStorage(UserAccount account){
        this.account = account;
        try {
            FileOutputStream fos = context.openFileOutput("account.dat", Context.MODE_PRIVATE);
            ObjectOutputStream o = new ObjectOutputStream(fos);

            o.writeObject(account);
            o.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private UserAccount readAccountFromInternalStorage(Context context){
        try {
            File file = context.getFileStreamPath("account.dat");
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream o = new ObjectInputStream(fis);
            return (UserAccount) o.readObject();
        } catch (IOException | ClassNotFoundException e) {
            Log.e(TAG, "No logged account");
        }
        return null;
    }

    private void deleteAccountFromInternalStorage(Context context){
        context.deleteFile("account.dat");
    }
}
