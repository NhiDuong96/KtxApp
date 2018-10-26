package com.example.minhnhi.quanlyktx;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import com.example.minhnhi.quanlyktx.utils.OnSlideAnimationEndListener;
import com.example.minhnhi.quanlyktx.utils.OnSlideAnimationStartListener;

public class KtxActivity extends AppCompatActivity implements
        OnSlideAnimationEndListener,
        OnSlideAnimationStartListener, View.OnTouchListener{
    private boolean mIsAnimating = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ktx);

        BuildingInfoFragment buildingInfoFragment = new BuildingInfoFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, buildingInfoFragment)
                .commit();
        buildingInfoFragment.setClickListener(this);
    }

    private void switchFragments () {
        if (mIsAnimating) {
            return;
        }
        mIsAnimating = true;
        RoomDetailsFragment roomDetailsFragment = new RoomDetailsFragment();
        roomDetailsFragment.setOnSlideFragmentAnimationEnd(this);

        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.animator.slide_fragment_in, 0, 0, R.animator.slide_fragment_out)
                .add(R.id.container, roomDetailsFragment)
                .addToBackStack("details")
                .commit();
    }

    @Override
    public void onAnimationEnd(View view) {
        mIsAnimating = false;
        view.setOnTouchListener(this);
    }

    @Override
    public void onAnimationStart() {
        switchFragments();
    }

    private boolean mSwiping = false;
    private boolean mViewPressed = false;
    private int mSwipeSlop = -1;
    private static final int SWIPE_DURATION = 350;
    private static final int MOVE_DURATION = 150;
    private float mStartY;
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
