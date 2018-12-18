package com.example.minhnhi.quanlyktx.view.user;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.minhnhi.quanlyktx.R;
import com.example.minhnhi.quanlyktx.beans.AccountUpdatePass;
import com.example.minhnhi.quanlyktx.beans.Gender;
import com.example.minhnhi.quanlyktx.beans.Notification;
import com.example.minhnhi.quanlyktx.beans.UserAccount;
import com.example.minhnhi.quanlyktx.beans.UserProfile;
import com.example.minhnhi.quanlyktx.cmd.ApiMethod;
import com.example.minhnhi.quanlyktx.cmd.ApiResponseClass;
import com.example.minhnhi.quanlyktx.cmd.BaseMsg;
import com.example.minhnhi.quanlyktx.cmd.ErrorCode;
import com.example.minhnhi.quanlyktx.utils.AccountManager;
import com.example.minhnhi.quanlyktx.utils.GsonDateFormatter;
import com.example.minhnhi.quanlyktx.utils.InputView;
import com.example.minhnhi.quanlyktx.utils.JsonAPI;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class UserInfoFragment extends Fragment implements View.OnClickListener {
    private List<Notification> notifications;
    private UserAccount account;
    private View view;
    private InputView mssv, name, className, address, old_pass, new_pass, confirm_pass;
    private EditText phone;
    private Spinner gender, year;

    private static List<String> years = new ArrayList<>();
    static {
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = thisYear; i >= 1975; i--)
        {
            years.add(String.valueOf(i));
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.user_info_fragment, container, false);
        Button b = view.findViewById(R.id.notification);
        b.setText(getUserNotification());
        b.setOnClickListener(this);
        view.findViewById(R.id.save).setOnClickListener(this);
        view.findViewById(R.id.reset).setOnClickListener(this);

        //UserProfile profile = loadUserProfile();
        UserProfile profile = this.account.getProfile();
        if(profile == null) return view;

        mssv = view.findViewById(R.id.mssv);
        mssv.setText(profile.getMssv());
        name = view.findViewById(R.id.name);
        name.setText(profile.getName());
        className = view.findViewById(R.id.className);
        className.setText(profile.getClassName());
        address = view.findViewById(R.id.address);
        address.setText(profile.getAddress());
        phone = view.findViewById(R.id.phone);
        phone.setText(profile.getPhone());

        gender = view.findViewById(R.id.gender);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, Gender.keys());
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        gender.setAdapter(adapter);
        gender.setSelection(profile.getGender());

        year = view.findViewById(R.id.year);
        adapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_item, years);
        year.setAdapter(adapter);
        int yearIndex = years.indexOf(profile.getYear());
        if(yearIndex != -1)
            year.setSelection(yearIndex);
        else
            year.setSelection(0);

        old_pass = view.findViewById(R.id.old_pass);
        old_pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        new_pass = view.findViewById(R.id.new_pass);
        new_pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        confirm_pass = view.findViewById(R.id.confirm_new_pass);
        confirm_pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        view.findViewById(R.id.update_pass).setOnClickListener(this);

        return view;
    }


    public void setUserAccount(UserAccount account){
        this.account = account;
    }


    public String getUserNotification(){
        notifications = new ArrayList<>();
        Resources res = getResources();
        String uri = res.getString(R.string.host)
                + res.getString(R.string.user_notification_uri)
                + account.getId();

        BaseMsg<List<Notification>> msg = new BaseMsg<>(uri, ApiMethod.GET, ApiResponseClass.NotificationResponse.class);
        msg.setGsonParser(GsonDateFormatter.getGson());
        msg.resolveDataOnMainThread();

        if(msg.getCode() == ErrorCode.SUCCESS){
            notifications = msg.getData();
        }
        else{
            notifications = new ArrayList<>();
        }

        return "Hiện tại bạn đang có "+ notifications.size() +" thông báo chưa đọc.";
    }

    public List<Notification> getUserNotifications(){
        if(notifications == null){
            notifications = new ArrayList<>();
        }
        return notifications;
    }

    @Override
    public void onClick(View vi) {
        switch (vi.getId()){
            case R.id.notification:
                if(notifications == null || notifications.size() == 0){
                    Toast.makeText(this.getContext(), "Hiện tại bạn không có thông báo nào!", Toast.LENGTH_LONG).show();
                    return;
                }
                UserNotificationFragment userNotificationFragment = new UserNotificationFragment();
                userNotificationFragment.setNotifications(notifications);
                getFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, userNotificationFragment)
                        .commit();
                break;
            case R.id.save:
                UserProfile profile = new UserProfile();
                //
                profile.setName(name.getText());
                profile.setAddress(address.getText());
                profile.setGender(gender.getSelectedItemPosition());
                profile.setPhone(phone.getText().toString());
                boolean success = updateUserProfile(profile);
                if(success){
                    Toast.makeText(this.getContext(), "Cập nhật thông tin thành công!", Toast.LENGTH_LONG).show();
                    account.updateProfile(profile);
                }
                else{
                    Toast.makeText(this.getContext(), "Cập nhật thông tin thất bại!", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.reset:
                name.setText("");
                year.setSelection(years.size()-1);
                phone.setText("");
                address.setText("");
                break;
            case R.id.update_pass:
                String oldPass = old_pass.getText();
                String newPass = new_pass.getText();
                String confPass = confirm_pass.getText();
                if(oldPass.isEmpty() || newPass.isEmpty()){
                    Toast.makeText(this.getContext(), "Hãy nhập thông tin thay đổi mật khẩu!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(!newPass.equals(confPass)){
                    Toast.makeText(this.getContext(), "Mật khẩu xác nhận không đúng!", Toast.LENGTH_SHORT).show();
                    return;
                }
                success = updateUserPassword(new AccountUpdatePass(name.getText(), oldPass,newPass));
                if(success){
                    Toast.makeText(this.getContext(), "Cập nhật thông tin thành công!", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(this.getContext(), "Cập nhật thông tin thất bại!", Toast.LENGTH_LONG).show();
                }
                ;
                break;
        }
    }

    public boolean updateUserProfile(UserProfile profile) {
        String host = getResources().getString(R.string.host);
        String uri = getResources().getString(R.string.update_user_detail_uri);
        String userId = String.valueOf(account.getId());

        @SuppressLint("StaticFieldLeak")
        AsyncTask<Void, Void, Boolean> task = new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                String json = new Gson().toJson(profile);
                try {
                    int resCode = JsonAPI.post(json, host + uri + userId);
                    if (resCode == HttpURLConnection.HTTP_OK) {
                        return true;
                    }
                } catch (IOException e) {
                    return false;
                }
                return false;
            }
        }.execute();

        try {
            return task.get();
        } catch (ExecutionException | InterruptedException e) {
            return false;
        }
    }

    public boolean updateUserPassword(AccountUpdatePass pass) {
        String host = getResources().getString(R.string.host);
        String uri = getResources().getString(R.string.change_password);
        String userId = String.valueOf(account.getId());

        @SuppressLint("StaticFieldLeak")
        AsyncTask<Void, Void, Boolean> task = new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                String json = new Gson().toJson(pass);
                try {
                    int resCode = JsonAPI.post(json, host + uri);
                    if (resCode == HttpURLConnection.HTTP_OK) {
                        return true;
                    }
                } catch (IOException e) {
                    return false;
                }
                return false;
            }
        }.execute();

        try {
            return task.get();
        } catch (ExecutionException | InterruptedException e) {
            return false;
        }
    }
}
