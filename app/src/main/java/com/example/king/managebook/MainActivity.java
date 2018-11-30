package com.example.king.managebook;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.king.managebook.adapter.FragmentAdapter;
import com.example.king.managebook.common.BottomNavigationViewHelper;
import com.example.king.managebook.common.UserAuth;
import com.example.king.managebook.common.Utils;
import com.example.king.managebook.custom.LoadingDialog;
import com.example.king.managebook.model.Profile;



import com.example.king.managebook.view.ProfileActivity;
import com.example.king.managebook.view.account.login.LoginActivity;
import com.example.king.managebook.view.confirmation.ManageOrderActivity;

import org.greenrobot.eventbus.EventBus;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener , BottomNavigationView.OnNavigationItemSelectedListener{
    BottomNavigationView bottomNavigationView;
    NavigationView navigationView;
    ViewPager viewPager;
    private LoadingDialog loadingDialog;
    private FragmentAdapter fragmentAdapter;
    View userHeaderView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        viewPager = findViewById(R.id.view_pager);

        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(fragmentAdapter);
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                bottomNavigationView.setSelectedItemId(fragmentAdapter.getItemID(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        loadingDialog = new LoadingDialog(this);
        navigationView.setNavigationItemSelectedListener(this);
        bottomNavigationView= findViewById(R.id.bottom_navigation);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        navigationView.removeHeaderView(userHeaderView);
        userHeaderView = navigationView.inflateHeaderView(R.layout.nav_header_main);
//        if (headerProfile.getName() != null) {
//            showHeaderProfile(headerProfile);
//        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()){
            case R.id.action_settings: {
                break;
            }

        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onDestroy() {
        //  Log.i(TAG, "onDestroy: ");
        super.onDestroy();

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (item.getItemId()) {
            case R.id.nav_logout: {
                UserAuth.saveAccessToken(this, null);
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
                break;
            }
            case R.id.nav_account: {
                if (Utils.checkNetwork(this)) {
                    startActivity(new Intent(this, ProfileActivity.class));
                } else {
                    Toast.makeText(this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.nav_manage:{
                startActivity(new Intent(MainActivity.this, ManageOrderActivity.class));
                break;
            }


            case R.id.nav_category:{
                viewPager.setCurrentItem(0);
                break;
            }
            case R.id.nav_search:{
                viewPager.setCurrentItem(1);
                break;
            }
            case R.id.nav_confirm:{
                viewPager.setCurrentItem(2);
                break;
            }
            case R.id.nav_statictis:{
                viewPager.setCurrentItem(3);
                break;
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void showHeaderProfile(Profile headerProfile) {
        TextView txtFullname = userHeaderView.findViewById(R.id.txt_full_name);
        if (headerProfile != null) {
            txtFullname.setText(headerProfile.getName());
        }

    }


}
