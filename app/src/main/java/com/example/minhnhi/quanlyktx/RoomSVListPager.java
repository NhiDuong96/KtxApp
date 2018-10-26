package com.example.minhnhi.quanlyktx;

import android.content.Context;
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

import com.example.minhnhi.quanlyktx.utils.RoomPager;

import java.util.ArrayList;
import java.util.List;

public class RoomSVListPager extends Fragment implements RoomPager {
    List<StudentModel> studentModels;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.room_info_pager_2, container, false);
        ListView listView = view.findViewById(R.id.list_item);
        listView.setAdapter(new StudentAdapter(getContext(), 0));
        return view;
    }

    @Override
    public void loadData(int roomID) {
        Log.e("here", "load data");
        studentModels = new ArrayList<>();
        studentModels.add(new StudentModel("Dương Minh Nhi", "14T1", StudentStatus.RENT));
        studentModels.add(new StudentModel("Dương Minh Lâm", "14CD1", StudentStatus.RENT));
        studentModels.add(new StudentModel("Dương Minh Nhi", "14T1", StudentStatus.RENT));
        studentModels.add(new StudentModel("Dương Minh Lâm", "14CD1", StudentStatus.RENT));
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

        StudentAdapter(@NonNull Context context, int resource) {
            super(context, resource);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            StudentModel model = studentModels.get(position);
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
            return studentModels.size();
        }
    }
}
