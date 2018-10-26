package com.example.minhnhi.quanlyktx;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.minhnhi.quanlyktx.beans.Notification;
import com.example.minhnhi.quanlyktx.beans.UserAccount;
import com.example.minhnhi.quanlyktx.home.HomePage;
import com.example.minhnhi.quanlyktx.home.HomePageFactory;
import com.example.minhnhi.quanlyktx.home.HomePageMgr;
import com.example.minhnhi.quanlyktx.utils.AccountManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView recyclerView;
    private HomePageMgr homePageMgr = new HomePageMgr();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.home_title);

        //notification view
        recyclerView = findViewById(R.id.list_item);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        findViewById(R.id.next).setOnClickListener(this);
        findViewById(R.id.previous).setOnClickListener(this);

        homePageMgr.setPageFactory(new HomePageFactory());
        HomePage page = homePageMgr.getPage(HomePageFactory.NOTIFICATION);
        recyclerView.setAdapter(page.getAdapter());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.ktx:
                directToKtxPage();
                break;
            case R.id.student:
                if(isLogged()){
                    directToUserPage(getUserName());
                }else{
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivityForResult(intent,8001);
                }
                break;
            case R.id.activity:
                if(isLogged()){
                    directToActivityPage();
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
            Log.e(UserAccount.TAG, userAccount.getName());
            if(requestCode == 8001){
                directToUserPage(userAccount.getName());
            }else if(requestCode == 8002){
                directToActivityPage();
            }
        }
    }

    public void directToKtxPage(){
        Toast.makeText(this, "Ktx Page", Toast.LENGTH_SHORT).show();
    }

    public void directToUserPage(String name){
        Toast.makeText(this, "Student Page", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, UserActivity.class);
        intent.putExtra("user", name);
        startActivity(intent);
    }

    public void directToActivityPage(){
        Toast.makeText(this, "Activity Page", Toast.LENGTH_SHORT).show();
    }

    public boolean isLogged(){
       return AccountManager.getAccountManager().isLogged();
    }

    public String getUserName(){
        return AccountManager.getAccountManager().getLoggedAccount(getBaseContext()).getName();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.next:
                HomePage page = homePageMgr.getPage(homePageMgr.getPageFactory().nextPage());
                recyclerView.setAdapter(page.getAdapter());
                break;
            case R.id.previous:
                page = homePageMgr.getPage(homePageMgr.getPageFactory().previousPage());
                recyclerView.setAdapter(page.getAdapter());
                break;
        }
    }
}
