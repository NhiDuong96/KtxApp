package com.example.minhnhi.quanlyktx.view.ktx.pager;

import android.annotation.SuppressLint;
import android.content.Context;
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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.minhnhi.quanlyktx.R;
import com.example.minhnhi.quanlyktx.beans.Room;
import com.example.minhnhi.quanlyktx.beans.UserProfile;
import com.example.minhnhi.quanlyktx.cmd.StudentResponse;
import com.example.minhnhi.quanlyktx.utils.JsonAPI;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class RoomSVListPager extends RoomPager {
    private List<StudentModel> studentModels;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(studentModels == null && room != null)
            loadData(room);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.room_students_pager, container, false);
        ListView listView = view.findViewById(R.id.list_item);
        StudentAdapter adapter = new StudentAdapter(getContext(), studentModels);
        listView.setAdapter(adapter);
        return view;
    }

    public void loadData(Room room) {
        studentModels = new ArrayList<>();
        //load data here
        @SuppressLint("StaticFieldLeak")
        AsyncTask<Void, String, String> task = new AsyncTask<Void, String, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                String json = "";
                try {
                    Resources res = getResources();
                    String uri = res.getString(R.string.host) +
                            res.getString(R.string.get_student_in_room_uri) +
                            String.valueOf(room.getId());
                    json = JsonAPI.get(uri);
                    Log.e("data", json);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return json;
            }
        };
        task.execute();

        Gson gson = new Gson();
        try {
            StudentResponse result = gson.fromJson(task.get(), StudentResponse.class);
            for(UserProfile profile: result.entries){
                studentModels.add(new StudentModel(profile.getName(), profile.getClassName(), StudentStatus.RENT));
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public enum StudentStatus{
        RENT(0, "Đang Thuê"),
        REGISTER(1, "Đã đăng ký");
        private final int value;
        private final String description;
        StudentStatus(int i, String s) {
            value = i;
            description = s;
        }

        @Override
        public String toString() {
            return description;
        }
    }

    class StudentModel{
        String name, lop;
        StudentStatus status;
        StudentModel(String name, String lop, StudentStatus status){
            this.name = name;
            this.lop = lop;
            this.status = status;
        }

    }

    class StudentAdapter extends ArrayAdapter<StudentModel>{
        List<StudentModel> res;
        StudentAdapter(@NonNull Context context, List<StudentModel> resource) {
            super(context, 0);
            this.res = resource;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            StudentModel model = res.get(position);
            if(convertView == null){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.room_sv_item_layout, parent, false);
            }
            TextView t1,t2,t3;
            t1 = convertView.findViewById(R.id.name); t1.setText(model.name);
            t2 = convertView.findViewById(R.id.lop); t2.setText(model.lop);
            t3 = convertView.findViewById(R.id.status); t3.setText(model.status.toString());
            return convertView;
        }

        @Override
        public int getCount() {
            return res.size();
        }
    }
}
