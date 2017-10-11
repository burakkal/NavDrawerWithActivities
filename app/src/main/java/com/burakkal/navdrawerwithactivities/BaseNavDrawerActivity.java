package com.burakkal.navdrawerwithactivities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Burak on 11.10.2017.
 * burakkal54@gmail.com
 */

public class BaseNavDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final int DELAY_MILLIS = 250;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    private Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler();
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        setupActionBar();
        setupNavDrawer();
    }

    private void setupActionBar() {
        setSupportActionBar(toolbar);
    }

    private void setupNavDrawer() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        selectNavItem(item.getItemId());
        return false;
    }

    private void selectNavItem(int id) {
        Intent intent = null;

        if (id == R.id.nav_home && !(this instanceof MainActivity)) {
            intent = new Intent(this, MainActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK
                            | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        } else if (id == R.id.nav_gallery && !(this instanceof GalleryActivity)) {
            intent = new Intent(this, GalleryActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        } else if (id == R.id.nav_slideshow && !(this instanceof SlideShowActivity)) {
            intent = new Intent(this, SlideShowActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        } else if (id == R.id.nav_manage && !(this instanceof ToolsActivity)) {
            intent = new Intent(this, ToolsActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        }

        if (intent != null) {
            final Intent finalIntent = intent;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    launchActivity(finalIntent);
                }
            }, DELAY_MILLIS);
        }

        drawer.closeDrawer(GravityCompat.START);
    }

    private void launchActivity(Intent intent) {
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void setDrawerSelectedItem(@IdRes int menuItemId) {
        navigationView.setCheckedItem(menuItemId);
    }
}
