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

import com.example.minhnhi.quanlyktx.R;
import com.example.minhnhi.quanlyktx.beans.Bill;
import com.example.minhnhi.quanlyktx.beans.Room;
import com.example.minhnhi.quanlyktx.cmd.BillsResponse;
import com.example.minhnhi.quanlyktx.utils.JsonAPI;
import com.example.minhnhi.quanlyktx.utils.RoomPager;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

public class RoomBillPager extends Fragment implements RoomPager {

    private Room room;
    private Bill water_bill, electric_bill;
    private View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int thisMonth = Calendar.getInstance().get(Calendar.MONTH);
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        loadData(room, thisMonth, thisYear);
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
        if(electric_bill == null || water_bill == null) return;
        TextView tv;
        tv = view.findViewById(R.id.oldNumElectric); tv.setText(String.valueOf(electric_bill.getOldNumber()));
        tv = view.findViewById(R.id.newNumElectric); tv.setText(String.valueOf(electric_bill.getNewNumber()));
        tv = view.findViewById(R.id.revenueElectric);
        tv.setText(String.valueOf(electric_bill.getNewNumber() - electric_bill.getOldNumber()));
        tv = view.findViewById(R.id.costElectric); tv.setText(String.valueOf(electric_bill.getTotal()));

        tv = view.findViewById(R.id.oldNumWater); tv.setText(String.valueOf(water_bill.getOldNumber()));
        tv = view.findViewById(R.id.newNumWater); tv.setText(String.valueOf(water_bill.getNewNumber()));
        tv = view.findViewById(R.id.revenueWater);
        tv.setText(String.valueOf(water_bill.getNewNumber()- water_bill.getOldNumber()));
        tv = view.findViewById(R.id.costWater); tv.setText(String.valueOf(water_bill.getTotal()));

        String s = String.valueOf(water_bill.getTotal() + electric_bill.getTotal()) + " VNĐ";
        tv = view.findViewById(R.id.costTotal); tv.setText(s);
        CheckBox cb;
        cb = view.findViewById(R.id.payCb);
        if(water_bill.getStatus() == Bill.BillStatus.PAID && electric_bill.getStatus() == Bill.BillStatus.PAID){
            cb.setEnabled(false);
            cb.setText("Đã nộp");
        }else{
            cb.setEnabled(true);
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
                    publishProgress(json);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return json;
            }

            @Override
            protected void onProgressUpdate(String... values) {
                super.onProgressUpdate(values);

                String json = values[0];
                Gson gson = new Gson();
                BillsResponse result = gson.fromJson(json, BillsResponse.class);
                for(Bill bill: result.entries){
                    if(bill.getType() == Bill.BillType.ELECTRIC_BILL){
                        electric_bill = bill;
                    }
                    else if(bill.getType() == Bill.BillType.WATER_BILL){
                        water_bill = bill;
                    }
                }
            }
        };
        task.execute();
    }

    @Override
    public void setRoom(Room room) {
        this.room = room;
    }

}
