package com.example.minhnhi.quanlyktx.view.user;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.minhnhi.quanlyktx.R;
import com.example.minhnhi.quanlyktx.utils.TimeParser;

import java.util.List;

public class UserNotificationAdapter extends ArrayAdapter<ExpandableNotificationItem> {

    private List<ExpandableNotificationItem> mData;
    private int mLayoutViewResourceId;

    public UserNotificationAdapter(Context context, int layoutViewResourceId,
                                   List<ExpandableNotificationItem> data) {
        super(context, layoutViewResourceId, data);
        mData = data;
        mLayoutViewResourceId = layoutViewResourceId;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        final ExpandableNotificationItem object = mData.get(position);

        if(convertView == null) {
            LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
            convertView = inflater.inflate(mLayoutViewResourceId, parent, false);
        }

        LinearLayout linearLayout = (convertView.findViewById(R.id.item_linear_layout));
        LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams
                (AbsListView.LayoutParams.MATCH_PARENT, object.getCollapsedHeight());
        linearLayout.setLayoutParams(linearLayoutParams);

        TextView titleView = convertView.findViewById(R.id.title_view);
        TextView textView = convertView.findViewById(R.id.text_view);
        TextView dateView = convertView.findViewById(R.id.date);
        TextView timeView = convertView.findViewById(R.id.time);
        CheckBox checkBox = convertView.findViewById(R.id.select);
        checkBox.setOnCheckedChangeListener((compoundButton, b) -> {
               object.setSelected(b);
        });

        titleView.setText(object.getTitle());
        textView.setText(object.getText());
        dateView.setText(TimeParser.parse(object.getDate(), TimeParser.DATE_PATTERN_1));
        timeView.setText(TimeParser.parse(object.getDate(), TimeParser.TIME_PATTERN_1));

        convertView.setLayoutParams(new ListView.LayoutParams(
                AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT));

        ExpandingLayout expandingLayout = convertView.findViewById(R.id.expanding_layout);
        expandingLayout.setExpandedHeight(object.getExpandedHeight());
        expandingLayout.setSizeChangedListener(object);

        if (!object.isExpanded()) {
            expandingLayout.setVisibility(View.GONE);
        } else {
            expandingLayout.setVisibility(View.VISIBLE);
        }

        return convertView;
    }

}