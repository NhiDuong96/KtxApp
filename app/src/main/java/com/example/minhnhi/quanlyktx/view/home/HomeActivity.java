package com.example.minhnhi.quanlyktx.view.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.minhnhi.quanlyktx.R;
import com.example.minhnhi.quanlyktx.beans.UserAccount;
import com.example.minhnhi.quanlyktx.utils.AccountManager;
import com.example.minhnhi.quanlyktx.view.activity.RoomRegisterActivity;
import com.example.minhnhi.quanlyktx.view.ktx.KtxActivity;
import com.example.minhnhi.quanlyktx.view.login.LoginActivity;
import com.example.minhnhi.quanlyktx.view.user.UserActivity;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
   private NotificationListFragment notificationListFragment;
   public Toolbar toolbar;
    ActionBar actionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.home_title);
        findViewById(R.id.next).setOnClickListener(this);
        findViewById(R.id.previous).setOnClickListener(this);

        notificationListFragment = new NotificationListFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, notificationListFragment)
                .commit();
    }

    public void setToolbarTitle(String title) {
        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(title);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //AccountManager.getAccountManager().logOut(getBaseContext());
        switch (item.getItemId()){
            case R.id.ktx:
                directToKtxPage();
                break;
            case R.id.student:
                if(isLogged()){
                    directToUserPage(getUserAccount());
                }else{
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivityForResult(intent,8001);
                }
                break;
            case R.id.activity:
                directToActivityPage(getUserAccount());
                if(isLogged()){
                    directToActivityPage(getUserAccount());
                }else{
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivityForResult(intent,8002);
                }
                break;
        }

        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            UserAccount userAccount = (UserAccount) data.getSerializableExtra(UserAccount.TAG);
            Log.e(UserAccount.TAG, userAccount.getUserName());
            if(requestCode == 8001){
                directToUserPage(userAccount);
            }else if(requestCode == 8002){
                directToActivityPage(userAccount);
            }
        }
    }

    public void directToKtxPage(){
        //Toast.makeText(this, "Ktx Page", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, KtxActivity.class);

        startActivity(intent);
    }

    public void directToUserPage(UserAccount account){
        //Toast.makeText(this, "Student Page", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, UserActivity.class);
        intent.putExtra("account", account);
        startActivity(intent);
    }

    public void directToActivityPage(UserAccount account){
        //Toast.makeText(this, "Activity Page", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, RoomRegisterActivity.class);
        intent.putExtra("account", account);
        startActivity(intent);
    }

    public boolean isLogged(){
       return AccountManager.getAccountManager().isLogged(getBaseContext());
    }

    public UserAccount getUserAccount(){
        return AccountManager.getAccountManager().getLoggedAccount(getBaseContext());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.next:
                notificationListFragment.onNextPage();
                break;
            case R.id.previous:
                notificationListFragment.onPreviousPage();
                break;
        }
    }
}
