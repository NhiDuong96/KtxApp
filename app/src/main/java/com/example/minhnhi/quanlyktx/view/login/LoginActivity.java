package com.example.minhnhi.quanlyktx.view.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.minhnhi.quanlyktx.R;
import com.example.minhnhi.quanlyktx.beans.UserAccount;
import com.example.minhnhi.quanlyktx.utils.AccountManager;
import com.example.minhnhi.quanlyktx.utils.LinkText;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText name, password;
    private CheckBox remember;

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
        linkText.setClickListener(view -> Toast.makeText(LoginActivity.this, "Đang phát triển", Toast.LENGTH_LONG).show());

        linkText = findViewById(R.id.register);
        linkText.setClickListener(view ->{
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        name = findViewById(R.id.name);
        password = findViewById(R.id.password);
        remember = findViewById(R.id.remember);

        name.setText("123123");
        password.setText("123");
        remember.setChecked(true);
        findViewById(R.id.btn_sign_in).setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_sign_in:
                String nameStr = name.getText().toString(), passStr = password.getText().toString();
                if(nameStr.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Mã số sinh viên trống!", Toast.LENGTH_SHORT).show();
                    return;
                }else if(passStr.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Hãy nhập mật khẩu!", Toast.LENGTH_SHORT).show();
                    return;
                }

                AccountManager accountManager = AccountManager.getAccountManager();
                accountManager.setListener(new AccountManager.OnLoginSuccessListener() {
                    @Override
                    public void onLoggedSuccess(UserAccount account) {
                        Intent intent = new Intent();
                        intent.putExtra(UserAccount.TAG, account);
                        setResult(RESULT_OK, intent);
                        finish();
                    }

                    @Override
                    public void onLoggedFailed(String msg) {
                        Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
                Resources res = getResources();
                String uri = res.getString(R.string.host) + res.getString(R.string.login_uri);

                accountManager.logIn(this, nameStr, passStr, uri, remember.isChecked());
             break;
        }
    }
}
