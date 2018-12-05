package com.example.minhnhi.quanlyktx.utils;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.example.minhnhi.quanlyktx.R;
import com.example.minhnhi.quanlyktx.beans.UserAccount;
import com.example.minhnhi.quanlyktx.cmd.AccountRequest;
import com.google.gson.Gson;

import org.json.JSONException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.util.concurrent.ExecutionException;

public class AccountManager implements Serializable{
    private static final String TAG = "AccountManager";

    public interface OnLoginSuccessListener{
        void onLogged(UserAccount account);
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
    public boolean logIn(final Context context, String mssv, String password, String uri, boolean remember){
        AccountRequest req = new AccountRequest(mssv, "", password);
        AsyncTask<Void, Void, UserAccount> task = new AsyncTask<Void, Void, UserAccount>() {

            @Override
            protected UserAccount doInBackground(Void... voids) {
                UserAccount user = null;
                try {
                    String json = JsonAPI.postForResponse(new Gson().toJson(req), uri, HttpURLConnection.HTTP_OK);
                    user = UserAccount.parseJson(json);
                    isLogged = true;
                } catch (IOException | JSONException e) {
                    isLogged = false;
                }
                return user;
            }
        };
        task.execute();
        try {
            this.account = task.get();
            if(isLogged && listener != null){
                listener.onLogged(this.account);
            }
            if(remember){
                saveAccountToInternalStorage(context, account);
            }
            return isLogged;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return false;
        }
    }

    @SuppressLint("StaticFieldLeak")
    public boolean signIn(final Context context, String mssv, String password, String uri){
        AccountRequest req = new AccountRequest(mssv, "", password);
        AsyncTask<Void, Void, UserAccount> task = new AsyncTask<Void, Void, UserAccount>() {

            @Override
            protected UserAccount doInBackground(Void... voids) {
                UserAccount user = null;
                try {
                    String json = JsonAPI.postForResponse(new Gson().toJson(req), uri, HttpURLConnection.HTTP_OK);
                    user = new Gson().fromJson(json, UserAccount.class);
                    isLogged = true;
                } catch (IOException e) {
                    isLogged = false;
                }
                return user;
            }
        };
        task.execute();
        try {
            this.account = task.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            this.isLogged = false;
        }
        if(isLogged && listener != null){
            listener.onLogged(this.account);
            saveAccountToInternalStorage(context, account);
        }
        return isLogged;
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
        if(account == null){
            account = readAccountFromInternalStorage(context);
        }
        return account;
    }

    private void saveAccountToInternalStorage(Context context, UserAccount account){
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
