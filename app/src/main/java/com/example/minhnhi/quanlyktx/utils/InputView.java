package com.example.minhnhi.quanlyktx.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.minhnhi.quanlyktx.R;

public class InputView extends LinearLayout {
    private EditText et;
    public InputView(Context context) {
        super(context);
        initView(null);
    }

    public InputView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(attrs);
    }

    public InputView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(attrs);
    }

    private void initView(AttributeSet attrs){
        View view = inflate(getContext(), R.layout.input_view_layout, null);
        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.InputView,
                0,0
        );
        String label;
        boolean ediable;
        int inputType;
        try {
            label = a.getString(R.styleable.InputView_label);
            ediable = a.getBoolean(R.styleable.InputView_editable, true);
            inputType = a.getInt(R.styleable.InputView_inputType, 1);
        } finally {
            a.recycle();
        }
        TextView lb = view.findViewById(R.id.label);
        et = view.findViewById(R.id.input);
        lb.setText(label);
        et.setEnabled(ediable);
        et.setInputType(inputType);
        addView(view);
    }

    public void setText(String text){
        et.setText(text);
    }
}
