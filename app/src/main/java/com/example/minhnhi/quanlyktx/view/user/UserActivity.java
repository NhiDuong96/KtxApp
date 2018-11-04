package com.example.minhnhi.quanlyktx.view.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.minhnhi.quanlyktx.R;
import com.example.minhnhi.quanlyktx.beans.UserAccount;
import com.example.minhnhi.quanlyktx.utils.AccountManager;
import com.example.minhnhi.quanlyktx.view.home.HomeActivity;
import com.example.minhnhi.quanlyktx.view.login.LoginActivity;

public class UserActivity extends AppCompatActivity{
    private UserAccount account;
    private UserInfoFragment userInfoFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        Intent intent = getIntent();
        account = (UserAccount) intent.getSerializableExtra("account");

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.user_title);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        userInfoFragment = new UserInfoFragment();
        userInfoFragment.setUserAccount(account);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.frame_layout, userInfoFragment)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()){
            case android.R.id.home:
                Intent homeIntent = new Intent(this, HomeActivity.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
                break;
            case R.id.user:
                userInfoFragment = new UserInfoFragment();
                userInfoFragment.setUserAccount(account);
                fragment = userInfoFragment;
                break;
            case R.id.notification:
                UserNotificationFragment userNotificationFragment = new UserNotificationFragment();
                userNotificationFragment.setNotifications(userInfoFragment.getUserNotifications());
                fragment = userNotificationFragment;
                break;
            case R.id.logout:
                AccountManager.getAccountManager().logOut(getBaseContext());
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
        }
        if(fragment == null) return true;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .commit();
        return true;
    }
}