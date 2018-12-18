package com.example.minhnhi.quanlyktx.view.user;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.minhnhi.quanlyktx.R;
import com.example.minhnhi.quanlyktx.beans.Notification;
import com.example.minhnhi.quanlyktx.cmd.ApiMethod;
import com.example.minhnhi.quanlyktx.cmd.BaseMsg;
import com.example.minhnhi.quanlyktx.cmd.ErrorCode;
import com.example.minhnhi.quanlyktx.utils.JsonAPI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class UserNotificationFragment extends Fragment implements View.OnClickListener {
    private List<ExpandableNotificationItem> notifications;
    private UserNotificationAdapter adapter;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.user_notification_fragment, container, false);

        adapter = new UserNotificationAdapter(getContext(), R.layout.user_notification_item_layout, notifications);
        ExpandingListView mListView = v.findViewById(R.id.list_item);
        mListView.setAdapter(adapter);

        v.findViewById(R.id.read).setOnClickListener(this);
        v.findViewById(R.id.delete).setOnClickListener(this);
        return v;
    }

    public void setNotifications(List<Notification> nfs) {
       notifications = new ArrayList<>();
        for(Notification nf: nfs){
            int CELL_DEFAULT_HEIGHT = 200;
            notifications.add(new ExpandableNotificationItem(nf.getId(), nf.getTitle(),
                    nf.getPostDate(), CELL_DEFAULT_HEIGHT, nf.getContent()));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View view) {
        Resources res = getResources();
        switch (view.getId()){
            case R.id.read:
                Iterator<ExpandableNotificationItem> ite = notifications.iterator();
                while(ite.hasNext()){
                    ExpandableNotificationItem nf = ite.next();
                    if(nf.isSelected()){
                        boolean done = updateNotification(nf, res.getString(R.string.host) +
                                res.getString(R.string.read_notification_uri));
                        if(done){
                            ite.remove();
                        }
                    }
                }

                break;
            case R.id.delete:
                ite = notifications.iterator();
                while(ite.hasNext()){
                    ExpandableNotificationItem nf = ite.next();
                    if(nf.isSelected()){
                        boolean done =  updateNotification(nf,res.getString(R.string.host) +
                                res.getString(R.string.delete_notification_uri));
                        if(done){
                            ite.remove();
                        }
                    }
                }
                break;
        }
        adapter.notifyDataSetChanged();
    }

    @SuppressLint("StaticFieldLeak")
    private boolean updateNotification(ExpandableNotificationItem nf, String uri){
        BaseMsg<Void> msg = new BaseMsg<>(uri + nf.getId(), ApiMethod.GET);
        msg.exec(null);
        return msg.getCode() == ErrorCode.SUCCESS;
    }
}
