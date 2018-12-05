package com.example.minhnhi.quanlyktx.view.ktx.pager;

import android.annotation.SuppressLint;
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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.minhnhi.quanlyktx.R;
import com.example.minhnhi.quanlyktx.beans.Bill;
import com.example.minhnhi.quanlyktx.beans.Room;
import com.example.minhnhi.quanlyktx.cmd.BillsResponse;
import com.example.minhnhi.quanlyktx.utils.JsonAPI;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

public class RoomBillPager extends RoomPager {

    private static Bill bill;
    private View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int thisMonth = Calendar.getInstance().get(Calendar.MONTH);
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        if(bill == null && room != null){
            loadData(room, thisMonth + 1, thisYear);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.room_bill_pager, container, false);

        ArrayList<Integer> years = new ArrayList<>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = thisYear; i >= 2000; i--)
        {
            years.add(i);
        }

        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_item, years);
        //spinner to enter this list to
        Spinner spinYear = view.findViewById(R.id.year);
        spinYear.setAdapter(adapter);


        ArrayList<Integer> months = new ArrayList<>();
        int thisMonth = Calendar.getInstance().get(Calendar.MONTH);
        for (int i = 1; i <= 12; i++)
        {
            months.add(i);
        }
        adapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_item, months);
        int pos = months.indexOf(thisMonth);
        //spinner to enter this list to
        Spinner spinMonth = view.findViewById(R.id.month);
        spinMonth.setAdapter(adapter);
        spinMonth.setSelection(pos+1);

        this.view = view;
        Button btn = view.findViewById(R.id.view);
        btn.setOnClickListener(view1 -> {
            int month = (int) spinMonth.getSelectedItem();
            int year = (int) spinYear.getSelectedItem();
            loadData(room,month,year);
            viewData();
        });
        viewData();
        return view;
    }

    private void viewData(){
        TextView tv;
        tv = view.findViewById(R.id.oldNumElectric);
        tv.setText(String.valueOf(bill.getOld_number_elec()));
        tv = view.findViewById(R.id.newNumElectric);
        tv.setText(String.valueOf(bill.getNew_number_elec()));
        tv = view.findViewById(R.id.revenueElectric);
        tv.setText(String.valueOf(bill.getNew_number_elec() - bill.getOld_number_elec()));
        tv = view.findViewById(R.id.totalElectric);
        tv.setText(String.valueOf(bill.getTotal_elec()));
        tv = view.findViewById(R.id.costElec);
        tv.setText(String.valueOf(bill.getCost_elec()));
        tv = view.findViewById(R.id.levelElec);
        tv.setText(String.valueOf(bill.getLevel_elec()));

        tv = view.findViewById(R.id.oldNumWater);
        tv.setText(String.valueOf(bill.getOld_number_water()));
        tv = view.findViewById(R.id.newNumWater);
        tv.setText(String.valueOf(bill.getNew_number_elec()));
        tv = view.findViewById(R.id.revenueWater);
        tv.setText(String.valueOf(bill.getNew_number_water()- bill.getOld_number_water()));
        tv = view.findViewById(R.id.totalWater);
        tv.setText(String.valueOf(bill.getTotal_water()));
        tv = view.findViewById(R.id.costWater);
        tv.setText(String.valueOf(bill.getCost_water()));
        tv = view.findViewById(R.id.levelWater);
        tv.setText(String.valueOf(bill.getLevel_water()));

        String s = String.valueOf(bill.getTotal()) + " VNĐ";
        tv = view.findViewById(R.id.costTotal); tv.setText(s);
        CheckBox cb;
        cb = view.findViewById(R.id.payCb);
        if(bill.getStatus() == Bill.BillStatus.PAID){
            cb.setEnabled(true);
            cb.setText("Đã nộp");
        }else{
            cb.setEnabled(false);
            cb.setText("Chưa nộp");
        }
    }

    private void loadData(Room room, int thisMonth, int thisYear)
    {
        //load data
        @SuppressLint("StaticFieldLeak")
        AsyncTask<Void, String, String> task = new AsyncTask<Void, String, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                String json = "";
                try {
                    Resources res = getResources();
                    String uri = res.getString(R.string.host) +
                            res.getString(R.string.get_bill_in_room_uri) +
                            String.valueOf(room.getId()) + "/" +
                            String.valueOf(thisMonth) + "/" +
                            String.valueOf(thisYear);
                    json = JsonAPI.get(uri);
                    Log.e("data", json);
                } catch (IOException e) {
                    onBillNotExist();
                }
                return json;
            }
        };
        task.execute();

        try {
            String resultStr = task.get();
            if(!resultStr.isEmpty()){
                Gson gson = new Gson();
                BillsResponse result = gson.fromJson(task.get(), BillsResponse.class);
                bill = result.entry;
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            onBillNotExist();
        }
    }

    private void onBillNotExist(){
        bill = new Bill();
    }
}
