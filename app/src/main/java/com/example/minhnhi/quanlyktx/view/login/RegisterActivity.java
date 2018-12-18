package com.example.minhnhi.quanlyktx.view.login;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.example.minhnhi.quanlyktx.R;

public class RegisterActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setTitle(R.string.register_title);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setIcon(R.mipmap.icons8_customer);
        }

        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, new RegisterUserAccountFragment())
                .addToBackStack(null)
                .commit();
    }


}
