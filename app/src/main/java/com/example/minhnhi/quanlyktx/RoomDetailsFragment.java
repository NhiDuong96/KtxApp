package com.example.minhnhi.quanlyktx;


import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
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

import com.example.minhnhi.quanlyktx.utils.OnSlideAnimationEndListener;

public class RoomDetailsFragment extends Fragment {
    private static final int NUM_PAGES = 4;
    private OnSlideAnimationEndListener mListener;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.room_details_fragment, container, false);
        mPager = view.findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getChildFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        return view;
    }


    @Override
    public Animator onCreateAnimator(int transit, boolean enter, int nextAnim)
    {
        int id = enter ? R.animator.slide_fragment_in : R.animator.slide_fragment_out;
        final Animator anim = AnimatorInflater.loadAnimator(getActivity(), id);
        if (enter) {
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    if(mListener != null)
                        mListener.onAnimationEnd(RoomDetailsFragment.this.getView());
                }
            });
        }
        return anim;
    }

    public void setOnSlideFragmentAnimationEnd(OnSlideAnimationEndListener listener)
    {
        mListener = listener;
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    RoomInfoPager pager = new RoomInfoPager();
                    pager.loadData(0);
                    return pager;
                case 1:
                    RoomSVListPager svPager = new RoomSVListPager();
                    svPager.loadData(0);
                    return svPager;
                case 2:
                    RoomBillPager billPager = new RoomBillPager();
                    billPager.loadData(0);
                    return billPager;
            }
            return new RoomInfoPager();
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}
