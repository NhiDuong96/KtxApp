package com.example.minhnhi.quanlyktx.view.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.minhnhi.quanlyktx.R;
import com.example.minhnhi.quanlyktx.beans.RegisterRoom;
import com.example.minhnhi.quanlyktx.beans.Room;
import com.example.minhnhi.quanlyktx.beans.UserAccount;
import com.example.minhnhi.quanlyktx.cmd.BaseMsg;
import com.example.minhnhi.quanlyktx.utils.JsonAPI;
import com.example.minhnhi.quanlyktx.view.home.HomeActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class RoomRegisterResultFragment extends Fragment {
    private RegisterRoom registerRoom;
    private UserAccount account;
    private View view;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            registerRoom();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void registerRoom() throws ExecutionException, InterruptedException {
        registerRoom.setUserId(account.getId());
        String uri = getResources().getString(R.string.host) + getResources().getString(R.string.register_room_uri);

        @SuppressLint("StaticFieldLeak")
        AsyncTask<Void, Void, Boolean> task = new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                try {
                    Gson gson = new Gson();
                    int code = JsonAPI.put(gson.toJson(registerRoom), uri);
                    Log.e("debug", String.valueOf(code));
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
                return true;
            }
        };
        task.execute();
        if(task.get()){
            viewResultSuccess();
        }
        else{
            viewResultFailed();
        }
    }

    private void viewResultSuccess() {
        TextView tv = view.findViewById(R.id.result);
        tv.setText(Html.fromHtml(convertToHtmlString()));
    }

    private String convertToHtmlString(){
        Room room = registerRoom.getRoom();
        String roomStr = room.getName() + " " + room.getFloor().getName() + " " + room.getFloor().getArea().getName();
        String html = "<i>Bạn đã đăng ký vào phòng: <br><b>" + roomStr + "</b></i><br>" +
                "<b>Phí nội trú:</b> " + room.getRoomCost() + " VNĐ<br>" +
                "<b>Số lượng đăng ký:</b> " + registerRoom.getNumRegister() + "<br>" +
                "<b>Ngày đăng ký:</b> " + registerRoom.getTimeRegister();
        return html;
    }

    private void viewResultFailed() {

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.room_register_result_fragment, container, false);
        this.view = view;
        view.findViewById(R.id.cancel).setOnClickListener(view1 -> {
            Intent intent = new Intent(RoomRegisterResultFragment.this.getContext(), HomeActivity.class);
            startActivity(intent);
        });
        return view;
    }

    public RegisterRoom getRegisterRoom() {
        return registerRoom;
    }

    public void setRegisterRoom(RegisterRoom registerRoom) {
        this.registerRoom = registerRoom;
    }

    public void setUserAccount(UserAccount account) {
        this.account = account;
    }
}
