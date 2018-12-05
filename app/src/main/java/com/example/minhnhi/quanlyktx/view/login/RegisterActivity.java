package com.example.minhnhi.quanlyktx.view.login;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.minhnhi.quanlyktx.R;
import com.example.minhnhi.quanlyktx.beans.UserAccount;
import com.example.minhnhi.quanlyktx.utils.AccountManager;
import com.example.minhnhi.quanlyktx.view.user.UserActivity;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText name, password, password_confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_account);

        setTitle(R.string.register_title);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setIcon(R.mipmap.icons8_customer);
        }


        name = findViewById(R.id.name);
        password = findViewById(R.id.password);
        password_confirm = findViewById(R.id.password_confirm);

        findViewById(R.id.btn_sign_in).setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_sign_in:
                String nameStr = name.getText().toString(),
                        passStr = password.getText().toString(),
                        passConf = password_confirm.getText().toString();
                if(nameStr.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Mã số sinh viên trống!", Toast.LENGTH_SHORT).show();
                    return;
                }else if(passStr.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Hãy nhập mật khẩu!", Toast.LENGTH_SHORT).show();
                    return;
                }else if(!passStr.equals(passConf)){
                    Toast.makeText(RegisterActivity.this, "Mật khẩu xác nhận không khớp!", Toast.LENGTH_SHORT).show();
                    return;
                }

                AccountManager accountManager = AccountManager.getAccountManager();
                accountManager.setListener(account -> {
                    Intent intent = new Intent(RegisterActivity.this, UserActivity.class);
                    intent.putExtra(UserAccount.TAG, account);
                    startActivity(intent);
                });
                Resources res = getResources();
                String uri = res.getString(R.string.host) + res.getString(R.string.sign_in_uri);

                boolean success = accountManager.signIn(this, nameStr, passStr, uri);
                if(!success){
                    Toast.makeText(RegisterActivity.this, "Đăng ký thất bại!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
