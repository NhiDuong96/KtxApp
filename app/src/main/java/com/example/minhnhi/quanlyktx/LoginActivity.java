package com.example.minhnhi.quanlyktx;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.minhnhi.quanlyktx.beans.UserAccount;
import com.example.minhnhi.quanlyktx.utils.AccountManager;
import com.example.minhnhi.quanlyktx.utils.LinkText;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText name, password;

    @Override
    protected void onStart() {
        super.onStart();
        AccountManager.getAccountManager().logOut(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setTitle(R.string.login_title);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setIcon(R.mipmap.icons8_customer);
        }

        //element

        LinkText linkText = findViewById(R.id.forgot_password);
        linkText.setClickListener(new LinkText.LinkTextClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "hello", Toast.LENGTH_LONG).show();
            }
        });

        name = findViewById(R.id.name);
        password = findViewById(R.id.password);
        findViewById(R.id.btn_sign_in).setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_sign_in:
                String nameStr = name.getText().toString(), passStr = password.getText().toString();
                if(nameStr.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Tên đăng nhập trống!", Toast.LENGTH_SHORT).show();
                    return;
                }else if(passStr.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Hãy nhập mật khẩu!", Toast.LENGTH_SHORT).show();
                    return;
                }
                UserAccount account = new UserAccount(nameStr, passStr);
                account.setApiUrl(getResources().getString(R.string.login_uri));
                CheckBox remember = findViewById(R.id.remember);

                AccountManager.getAccountManager().logIn(this, account, remember.isChecked(), new AccountManager.OnLoginSuccessListener(){
                    @Override
                    public void onLogged(UserAccount account) {
                        Intent intent = new Intent();
                        intent.putExtra(UserAccount.TAG, account);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });
             break;
        }
    }
}
