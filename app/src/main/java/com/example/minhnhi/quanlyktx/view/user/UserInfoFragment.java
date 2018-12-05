package com.example.minhnhi.quanlyktx.view.user;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.minhnhi.quanlyktx.R;
import com.example.minhnhi.quanlyktx.beans.Gender;
import com.example.minhnhi.quanlyktx.beans.Notification;
import com.example.minhnhi.quanlyktx.beans.UserAccount;
import com.example.minhnhi.quanlyktx.beans.UserProfile;
import com.example.minhnhi.quanlyktx.cmd.NotificationsResponse;
import com.example.minhnhi.quanlyktx.cmd.UserProfileResponse;
import com.example.minhnhi.quanlyktx.utils.GsonDateFormatter;
import com.example.minhnhi.quanlyktx.utils.InputView;
import com.example.minhnhi.quanlyktx.utils.JsonAPI;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class UserInfoFragment extends Fragment implements View.OnClickListener {
    private List<Notification> notifications;
    private UserAccount account;
    private View view;
    private EditText birthday;
    Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.MONTH, monthOfYear);
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        updateLabel();
    };

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
        InputView v = view.findViewById(R.id.mssv);
        v.setText(profile.getMssv());
        v = view.findViewById(R.id.name);
        v.setText(profile.getName());
        v = view.findViewById(R.id.className);
        v.setText(profile.getClassName());
        v = view.findViewById(R.id.address);
        v.setText(profile.getAddress());

        EditText editText = view.findViewById(R.id.phone);
        editText.setText(profile.getPhone());

        Spinner spinner = view.findViewById(R.id.gender);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, Gender.keys());
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinner.setAdapter(adapter);
        spinner.setSelection(Gender.getGenderIndexByCode(profile.getGender()));

        spinner = view.findViewById(R.id.year);
        adapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_item, years);
        spinner.setAdapter(adapter);
        spinner.setSelection(years.indexOf(profile.getYear()));

        return view;
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        birthday.setText(sdf.format(myCalendar.getTime()));
    }

    public void setUserAccount(UserAccount account){
        this.account = account;
    }

//    public UserProfile loadUserProfile(){
//        UserProfile profile = null;
//        Resources res = getResources();
//
//        @SuppressLint("StaticFieldLeak")
//        AsyncTask<Void, Void, UserProfile> task = new AsyncTask<Void, Void, UserProfile>() {
//            @Override
//            protected UserProfile doInBackground(Void... voids) {
//                String json;
//                try {
//                    json = JsonAPI.get(res.getString(R.string.host)
//                            + res.getString(R.string.user_info_uri) +
//                            + account.getId());
//                    Log.e("data", json);
//                    return new Gson().fromJson(json, UserProfileResponse.class).entry;
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                return null;
//            }
//        };
//        task.execute();
//        try {
//            profile = task.get();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//        return profile;
//    }

    public String getUserNotification(){
        notifications = new ArrayList<>();
        Resources res = getResources();

        @SuppressLint("StaticFieldLeak")
        AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                String json = "";
                try {
                    json = JsonAPI.get(res.getString(R.string.host)
                            + res.getString(R.string.user_notification_uri)
                            + account.getId());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return json;
            }
        };
        task.execute();
        Gson gson = GsonDateFormatter.getGson();
        try {
            notifications = gson.fromJson(task.get(), NotificationsResponse.class).entries;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
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
                UserNotificationFragment userNotificationFragment = new UserNotificationFragment();
                userNotificationFragment.setNotifications(notifications);
                getFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, userNotificationFragment)
                        .commit();
                break;
            case R.id.save:

                break;
            case R.id.reset:
                Spinner spinner = view.findViewById(R.id.year);
                spinner.setSelection(years.size()-1);
                EditText ed;
                ed = view.findViewById(R.id.phone);
                ed.setText("");
                InputView v;
                v = view.findViewById(R.id.className);
                v.setText("");
                v = view.findViewById(R.id.address);
                v.setText("");
                break;
        }
    }
}
