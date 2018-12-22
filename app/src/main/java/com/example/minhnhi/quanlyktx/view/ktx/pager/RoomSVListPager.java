package com.example.minhnhi.quanlyktx.view.ktx.pager;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.minhnhi.quanlyktx.R;
import com.example.minhnhi.quanlyktx.beans.Room;
import com.example.minhnhi.quanlyktx.beans.UserProfile;
import com.example.minhnhi.quanlyktx.cmd.ApiLoadingObserverAdapter;
import com.example.minhnhi.quanlyktx.cmd.ApiMethod;
import com.example.minhnhi.quanlyktx.cmd.ApiResponse;
import com.example.minhnhi.quanlyktx.cmd.ApiResponseClass;
import com.example.minhnhi.quanlyktx.cmd.BaseMsg;
import com.example.minhnhi.quanlyktx.cmd.ErrorCode;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class RoomSVListPager extends RoomPager {
    private List<StudentModel> studentModels;
    private StudentAdapter adapter;

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
        adapter = new StudentAdapter(getContext(), studentModels);
        listView.setAdapter(adapter);
        return view;
    }

    public void loadData(Room room) {
        studentModels = new ArrayList<>();
        Resources res = getResources();
        String uri = res.getString(R.string.host) +
                res.getString(R.string.get_student_in_room_uri) +
                String.valueOf(room.getId());

        BaseMsg<Void> msg = new BaseMsg<>(uri, ApiMethod.GET);
        msg.exec(new ApiLoadingObserverAdapter(){
            @Override
            public void onLoadingSuccess(String json) {
                try {
                    List<UserProfile> profiles = UserProfile.parseListProfiles(json);
                    for(UserProfile profile: profiles){
                        studentModels.add(new StudentModel(profile.getName(), profile.getClassName(), StudentStatus.RENT));
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
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
