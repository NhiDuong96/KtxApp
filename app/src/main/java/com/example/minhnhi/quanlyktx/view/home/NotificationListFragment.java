package com.example.minhnhi.quanlyktx.view.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.minhnhi.quanlyktx.R;

public class NotificationListFragment extends Fragment {
    private RecyclerView recyclerView;
    private HomePageMgr homePageMgr = new HomePageMgr();
    private NotificationAdapter adapter;
    private HomePage page;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.notification_layout, container, false);

        //notification view
        recyclerView = view.findViewById(R.id.list_item);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        homePageMgr.setPageFactory(new HomePageFactory());
        page = homePageMgr.getPage(HomePageFactory.NOTIFICATION);
        adapter = page.getAdapter();
        adapter.setOnItemClickListener(listener);
        recyclerView.setAdapter(adapter);
        return view;
    }

    public void onNextPage(){
        page = homePageMgr.getPage(homePageMgr.getPageFactory().nextPage());
        adapter = page.getAdapter();
        adapter.setOnItemClickListener(listener);
        recyclerView.setAdapter(adapter);
    }

    public void onPreviousPage(){
        page = homePageMgr.getPage(homePageMgr.getPageFactory().previousPage());
        adapter = page.getAdapter();
        adapter.setOnItemClickListener(listener);
        recyclerView.setAdapter(adapter);
    }

    private AdapterView.OnItemClickListener listener = (adapterView, view, i, l) -> {
        NotificationDetailsFragment detailsFragment = new NotificationDetailsFragment();
        detailsFragment.setNotification(page.getItem(i));
        getFragmentManager().beginTransaction()
                .replace(R.id.container, detailsFragment)
                .addToBackStack(null)
                .commit();
    };
}