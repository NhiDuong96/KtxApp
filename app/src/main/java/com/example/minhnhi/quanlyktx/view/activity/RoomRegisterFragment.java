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
import android.widget.Toast;

import com.example.minhnhi.quanlyktx.R;
import com.example.minhnhi.quanlyktx.beans.RegisterResult;
import com.example.minhnhi.quanlyktx.beans.RegisterRoom;
import com.example.minhnhi.quanlyktx.beans.Room;
import com.example.minhnhi.quanlyktx.beans.UserAccount;
import com.example.minhnhi.quanlyktx.cmd.ApiMethod;
import com.example.minhnhi.quanlyktx.cmd.BaseMsg;
import com.example.minhnhi.quanlyktx.cmd.ErrorCode;
import com.example.minhnhi.quanlyktx.utils.JsonAPI;
import com.example.minhnhi.quanlyktx.utils.TimeParser;
import com.example.minhnhi.quanlyktx.view.home.HomeActivity;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class RoomRegisterFragment extends Fragment {
    private RegisterRoom registerRoom;
    private RegisterResult registerResult;
    private UserAccount account;
    private View view;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(registerResult == null) {
            try {
                registerRoom();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        else{
            viewResult();
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
            Room room = registerRoom.getRoom();
            registerResult = new RegisterResult();
            registerResult.setUserId(registerRoom.getUserId());
            registerResult.setRoomName(room.getName());
            registerResult.setFloorName(room.getFloorName());
            registerResult.setAreaName(room.getAreaName());
            registerResult.setNumber(registerRoom.getNumRegister());
            registerResult.setTimeRegister(Long.parseLong(registerRoom.getTimeRegister()));
            viewResult();
        }
    }

    private boolean cancelRegisterRoom(){
        String uri = getResources().getString(R.string.host) +
                getResources().getString(R.string.cancel_register_room_uri) +
                registerResult.getUserId();

        BaseMsg<Void> msg = new BaseMsg<>(uri, ApiMethod.GET);
        msg.exec(null);
        Log.e("debug", String.valueOf(msg.getCode()));
        return msg.getCode() == ErrorCode.SUCCESS;
    }

    private void viewResult() {
        TextView tv = view.findViewById(R.id.result);

        String roomStr = registerResult.getRoomName() + " " + registerResult.getFloorName()
                + " " + registerResult.getAreaName();
        Date d = new Date(registerResult.getTimeRegister());

        String html = "<i>Bạn đã đăng ký vào phòng: <br><b>" + roomStr + "</b></i><br>" +
                "<b>Số lượng đăng ký:</b> " + registerResult.getNumber() + "<br>" +
                "<b>Ngày đăng ký:</b> " + TimeParser.parse(d, TimeParser.DATE_PATTERN_1) + "<br>" +
                "<b>Giờ đăng ký:</b> " + TimeParser.parse(d, TimeParser.TIME_PATTERN_1);
        tv.setText(Html.fromHtml(html));
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.room_register_result_fragment, container, false);
        this.view = view;
        view.findViewById(R.id.cancel).setOnClickListener(view1 -> {
            if(cancelRegisterRoom()) {
                Toast.makeText(getContext(), "Huỷ đăng ký thành công!", Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(getContext(), "Huỷ đăng ký thất bại!", Toast.LENGTH_LONG).show();
            }
            Intent intent = new Intent(RoomRegisterFragment.this.getContext(), HomeActivity.class);
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

    public RegisterResult getRegisterResult() {
        return registerResult;
    }

    public void setRegisterResult(RegisterResult registerResult) {
        this.registerResult = registerResult;
    }
}
