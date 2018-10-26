package com.example.minhnhi.quanlyktx;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.minhnhi.quanlyktx.utils.RoomPager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RoomBillPager extends Fragment implements RoomPager {

    private int roomID;
    Bill water_bill, electric_bill;
    View view;

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
            loadData(roomID,month,year);
            viewData();
        });
        viewData();
        return view;
    }

    private void viewData(){
        TextView tv;
        tv = view.findViewById(R.id.oldNumElectric); tv.setText(String.valueOf(electric_bill.oldNum));
        tv = view.findViewById(R.id.newNumElectric); tv.setText(String.valueOf(electric_bill.newNum));
        tv = view.findViewById(R.id.revenueElectric); tv.setText(String.valueOf(electric_bill.revenueNum));
        tv = view.findViewById(R.id.costElectric); tv.setText(String.valueOf(electric_bill.cost));

        tv = view.findViewById(R.id.oldNumWater); tv.setText(String.valueOf(water_bill.oldNum));
        tv = view.findViewById(R.id.newNumWater); tv.setText(String.valueOf(water_bill.newNum));
        tv = view.findViewById(R.id.revenueWater); tv.setText(String.valueOf(water_bill.revenueNum));
        tv = view.findViewById(R.id.costWater); tv.setText(String.valueOf(water_bill.cost));

        tv = view.findViewById(R.id.costTotal); tv.setText(String.valueOf(water_bill.cost + electric_bill.cost) + " VNĐ");
        CheckBox cb;
        cb = view.findViewById(R.id.payCb);
        if(water_bill.isPay && electric_bill.isPay){
            cb.setEnabled(false);
            cb.setText("Đã nộp");
        }else{
            cb.setEnabled(true);
            cb.setText("Chưa nộp");
        }
    }

    @Override
    public void loadData(int roomID) {
        this.roomID = roomID;
        int thisMonth = Calendar.getInstance().get(Calendar.MONTH);
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        //load data
        electric_bill = new Bill(12239, 12383, 144,
                247800, true, BillType.ELECTRIC_BILL);
        water_bill = new Bill(12239, 12383, 144,
                247800, true, BillType.WATER_BILL);

    }

    private void loadData(int roomID, int thisMonth, int thisYear)

    {
        //load data
        electric_bill = new Bill(1229, 123, 144,
                247800, true, BillType.ELECTRIC_BILL);
        water_bill = new Bill(122, 2383, 144,
                247800, true, BillType.WATER_BILL);
    }

    enum BillType{
        ELECTRIC_BILL,
        WATER_BILL
    }

    class Bill{
        int oldNum;
        int newNum;
        int revenueNum;
        long cost;
        boolean isPay;
        BillType billType;

        public Bill(int oldNum, int newNum, int revenueNum, long cost, boolean isPay, BillType billType) {
            this.oldNum = oldNum;
            this.newNum = newNum;
            this.revenueNum = revenueNum;
            this.cost = cost;
            this.isPay = isPay;
            this.billType = billType;
        }
    }

}
