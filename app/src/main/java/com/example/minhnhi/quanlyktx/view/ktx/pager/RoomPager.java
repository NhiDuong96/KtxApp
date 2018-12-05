package com.example.minhnhi.quanlyktx.view.ktx.pager;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.support.v4.app.Fragment;

import com.example.minhnhi.quanlyktx.R;
import com.example.minhnhi.quanlyktx.beans.Room;
import com.example.minhnhi.quanlyktx.utils.OnSlideAnimationEndListener;

public abstract class RoomPager extends Fragment {
    protected Room room;
    private OnSlideAnimationEndListener mListener;

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
                        mListener.onAnimationEnd(RoomPager.this.getView());
                }
            });
        }
        return anim;
    }

    public void setOnSlideFragmentAnimationEnd(OnSlideAnimationEndListener listener)
    {
        mListener = listener;
    }

    public void setRoom(Room room) {
        this.room = room;
    }


}
