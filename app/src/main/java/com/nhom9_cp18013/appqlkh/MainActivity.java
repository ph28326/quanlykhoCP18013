package com.nhom9_cp18013.appqlkh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationView;
import com.nhom9_cp18013.appqlkh.ui.HoaDonXuat.ListHoaDonXuatFragment;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawer;
    Toolbar toolbar;
    View mHeaderView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawer = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.tlbMain);
        // set toolbar thay th cho actionbar
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
     //   actionBar.setHomeAsUpIndicator(R.drawable.baseline_menu_24);
        actionBar.setDisplayHomeAsUpEnabled(true);
        FragmentManager manager = getSupportFragmentManager();
        NavigationView nv = findViewById(R.id.nav_view);
        // show
        mHeaderView = nv.getHeaderView(0);


    }
}