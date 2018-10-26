package com.example.minhnhi.quanlyktx.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.minhnhi.quanlyktx.R;

public class LinkText extends android.support.v7.widget.AppCompatTextView {
    public interface LinkTextClickListener{
        void onClick(View view);
    }
    private LinkTextClickListener linkTextClickListener;

    public LinkText(Context context) {
        super(context);
    }

    public LinkText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.LinkText,
                0, 0);
        int endIndex;
        int startIndex;
        try {
            startIndex = a.getInteger(R.styleable.LinkText_startIndex, 0);
            endIndex = a.getInteger(R.styleable.LinkText_endIndex, -1);
        } finally {
            a.recycle();
        }
        Spannable spannable = (Spannable) this.getText();
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                if(linkTextClickListener != null)
                    linkTextClickListener.onClick(view);
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                if (isPressed()) {
                    ds.setColor(Color.RED); // need put invalidate here to make text change to RED when pressed on Highlight Link
                    invalidate();
                } else {
                    ds.setColor(Color.BLUE);
                }
            }
        };
        spannable.setSpan(clickableSpan, startIndex, endIndex == -1 ? getText().length() : endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, BufferType.SPANNABLE);
        setHighlightColor(Color.TRANSPARENT);
        setMovementMethod(LinkMovementMethod.getInstance());
    }

    public void setClickListener(LinkTextClickListener clickListener){
        linkTextClickListener = clickListener;
    }

}
