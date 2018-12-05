package com.example.minhnhi.quanlyktx.view.ktx;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.minhnhi.quanlyktx.R;
import com.example.minhnhi.quanlyktx.beans.Room;
import com.example.minhnhi.quanlyktx.utils.OnSlideAnimationEndListener;
import com.example.minhnhi.quanlyktx.view.ktx.pager.RoomPagerFactory;

public class RoomDetailsFragment extends Fragment {
    private int num_pages;
    private OnSlideAnimationEndListener mListener;
    private Room room;
    private RoomPagerFactory pagerFactory;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.room_details_fragment, container, false);
        ViewPager mPager = view.findViewById(R.id.pager);
        PagerAdapter mPagerAdapter = new ScreenSlidePagerAdapter(getChildFragmentManager(), num_pages);
        mPager.setAdapter(mPagerAdapter);
        return view;
    }

    public void setOnSlideFragmentAnimationEnd(OnSlideAnimationEndListener listener)
    {
        mListener = listener;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public void setPagerFactory(RoomPagerFactory pagerFactory) {
        this.pagerFactory = pagerFactory;
        this.num_pages = pagerFactory.getNumOfPages();
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        int num_pages;
        ScreenSlidePagerAdapter(FragmentManager fm, int num_pages) {
            super(fm);
            this.num_pages = num_pages;
        }

        @Override
        public Fragment getItem(int position) {
           return pagerFactory.createPage(position, room, mListener);
        }

        @Override
        public int getCount() {
            return this.num_pages;
        }
    }
}
