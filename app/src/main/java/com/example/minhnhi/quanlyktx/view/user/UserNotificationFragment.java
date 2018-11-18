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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.minhnhi.quanlyktx.R;
import com.example.minhnhi.quanlyktx.beans.Notification;
import com.example.minhnhi.quanlyktx.utils.JsonAPI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
                updateNotification(res.getString(R.string.host) + res.getString(R.string.read_notification_uri));
                break;
            case R.id.delete:
                updateNotification(res.getString(R.string.host) + res.getString(R.string.delete_notification_uri));
                break;
        }
        notifications.removeIf(ExpandableNotificationItem::isSelected);
        adapter.notifyDataSetChanged();
    }

    @SuppressLint("StaticFieldLeak")
    private void updateNotification(String uri){
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    for(ExpandableNotificationItem nf: notifications){
                        if(nf.isSelected())
                            JsonAPI.post("", uri + nf.getId());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
    }
}
