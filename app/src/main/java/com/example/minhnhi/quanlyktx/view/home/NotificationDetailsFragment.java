package com.example.minhnhi.quanlyktx.view.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.minhnhi.quanlyktx.R;
import com.example.minhnhi.quanlyktx.beans.Notification;
import com.example.minhnhi.quanlyktx.utils.TimeParser;

public class NotificationDetailsFragment extends Fragment {
    private Notification notification;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.notification_details_fragment, container, false);
        TextView tv;
        tv = view.findViewById(R.id.title);
        tv.setText(notification.getTitle());
        tv = view.findViewById(R.id.date);
        tv.setText(TimeParser.parse(notification.getPostDate(), TimeParser.DATE_PATTERN_1));
        tv = view.findViewById(R.id.time);
        tv.setText(TimeParser.parse(notification.getPostDate(), TimeParser.TIME_PATTERN_1));
        tv = view.findViewById(R.id.content);
        tv.setText(Html.fromHtml(notification.getContent()));
        view.findViewById(R.id.back).setOnClickListener(view1 -> {
            getFragmentManager().popBackStack();
        });

        return view;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }
}
