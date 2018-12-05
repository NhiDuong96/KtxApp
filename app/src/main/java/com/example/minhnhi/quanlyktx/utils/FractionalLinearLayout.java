package com.example.minhnhi.quanlyktx.utils;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class FractionalLinearLayout extends LinearLayout {
    private float mYFraction;
    private int mScreenHeight;

    public FractionalLinearLayout(Context context) {
        super(context);
    }

    public FractionalLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mScreenHeight = h;
        setY(-mScreenHeight);
    }

    public float getyFraction(){
        return mYFraction;
    }

    public void setYFraction(float yFraction){
        mYFraction = yFraction;
        setY((mScreenHeight > 0) ? (-yFraction * mScreenHeight) : 0);
    }
}
