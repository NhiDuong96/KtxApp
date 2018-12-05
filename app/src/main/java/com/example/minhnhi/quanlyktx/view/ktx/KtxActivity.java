package com.example.minhnhi.quanlyktx.view.ktx;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import com.example.minhnhi.quanlyktx.R;
import com.example.minhnhi.quanlyktx.beans.Room;
import com.example.minhnhi.quanlyktx.utils.OnSlideAnimationEndListener;
import com.example.minhnhi.quanlyktx.utils.OnSlideAnimationStartListener;
import com.example.minhnhi.quanlyktx.view.ktx.pager.RoomPagerFactory;

public class KtxActivity extends AppCompatActivity implements
        OnSlideAnimationEndListener,
        OnSlideAnimationStartListener<Room>, View.OnTouchListener{
    private boolean mIsAnimating = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreate();
    }

    protected void onCreate(){
        setContentView(R.layout.activity_ktx);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.ktx_title);
            actionBar.setHomeAsUpIndicator(R.mipmap.home_icon);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
      
        KtxInfoFragment ktxInfoFragment = new KtxInfoFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, ktxInfoFragment)
                .commit();
        ktxInfoFragment.setClickListener(this);
    }

    private void switchFragments (Room room) {
        if (mIsAnimating) {
            return;
        }
        mIsAnimating = true;
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.animator.slide_fragment_in, 0, 0, R.animator.slide_fragment_out)
                .add(R.id.container, getRoomDetailsFragment(room))
                .addToBackStack("details")
                .commit();
    }

    public RoomDetailsFragment getRoomDetailsFragment(Room room){
        RoomDetailsFragment roomDetailsFragment = new RoomDetailsFragment();
        roomDetailsFragment.setRoom(room);
        roomDetailsFragment.setPagerFactory(new RoomPagerFactory());
        roomDetailsFragment.setOnSlideFragmentAnimationEnd(this);
        return roomDetailsFragment;
    }

    @Override
    public void onAnimationEnd(View view) {
        mIsAnimating = false;
        view.setOnTouchListener(this);
    }

    @Override
    public void onAnimationStart(Room room) {
        switchFragments(room);
    }

    private boolean mSwiping = false;
    private boolean mViewPressed = false;
    private int mSwipeSlop = -1;
    private static final int SWIPE_DURATION = 350;
    private static final int MOVE_DURATION = 150;
    private float mStartY;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(mSwipeSlop < 0){
            mSwipeSlop = ViewConfiguration.get(this).getScaledTouchSlop();
        }
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (mViewPressed) {
                    // Multi-item swipes not handled
                    return false;
                }
                mViewPressed = true;
                mStartY = event.getY();
                break;
            case MotionEvent.ACTION_CANCEL:
                v.setAlpha(1);
                v.setTranslationY(0);
                mViewPressed = false;
                break;
            case MotionEvent.ACTION_MOVE:
            {
                float y = event.getY() + v.getTranslationY();
                float delY = Math.abs(mStartY - y);
                if(!mSwiping){
                    if(delY > mSwipeSlop){
                        mSwiping = true;
                    }
                }else{
                    v.setTranslationY(y - mStartY);
                    v.setAlpha(1-delY/v.getHeight());
                }

            }break;
            case MotionEvent.ACTION_UP:
            {
                if(mSwiping){
                    float y = event.getY() + v.getTranslationY();
                    float delY = Math.abs(mStartY - y);
                    float fractionCovered;
                    float endY;
                    float endAlpha;
                    final boolean remove;
                    if(delY > v.getHeight()/3){
                        fractionCovered = delY / v.getHeight();
                        endY = -v.getHeight();
                        endAlpha = 0;
                        remove = true;
                    }else{
                        fractionCovered = 1 - (delY / v.getHeight());
                        endY = 0;
                        endAlpha = 1;
                        remove = false;
                    }
                    long duration = (int) ((1 - fractionCovered) * SWIPE_DURATION);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        v.animate().setDuration(duration)
                                .alpha(endAlpha)
                                .translationY(endY)
                                .withEndAction(() -> {
                                    if(remove){
                                        getSupportFragmentManager().popBackStackImmediate(null,
                                                FragmentManager.POP_BACK_STACK_INCLUSIVE);
                                    }else{
                                        mSwiping = false;
                                    }
                                });
                    }
                }
            }
            mViewPressed = false;
            break;
            default:
                return false;
        }
        return true;
    }
}
