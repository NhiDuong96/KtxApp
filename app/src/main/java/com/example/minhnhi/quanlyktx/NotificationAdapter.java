package com.example.minhnhi.quanlyktx;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.minhnhi.quanlyktx.beans.Notification;
import com.example.minhnhi.quanlyktx.utils.TimeParser;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private List<Notification> notificationList;

    public NotificationAdapter(List<Notification> notificationList){
        this.notificationList = notificationList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item_layout, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Notification notification = notificationList.get(position);
        holder.title.setText(notification.getTitle());
        holder.date.setText(TimeParser.parse(notification.getPostDate(), TimeParser.DATE_PATTERN_1));
        holder.time.setText(TimeParser.parse(notification.getPostDate(), TimeParser.TIME_PATTERN_1));
    }

    @Override
    public int getItemCount() {
        return this.notificationList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView title, date, time;

        ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
        }

    }
}
