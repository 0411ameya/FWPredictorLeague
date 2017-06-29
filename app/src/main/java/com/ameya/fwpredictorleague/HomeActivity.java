package com.ameya.fwpredictorleague;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import fragments.AllMatchDaysFragment;
import fragments.LeagueTableFragment;
import fragments.MatchDayFragment;
import fragments.MyFwplFragment;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(0);


        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.content_frame, new MyFwplFragment()).commit();

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
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        FragmentManager fm = getFragmentManager();

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_myFWPL) {
            FrameLayout fl = (FrameLayout) findViewById(R.id.content_frame);
            //fl.removeAllViews();
            fm.beginTransaction().replace(R.id.content_frame, new MyFwplFragment()).addToBackStack(null).commit();

        } else if (id == R.id.nav_matchday) {
            FrameLayout fl = (FrameLayout) findViewById(R.id.content_frame);
            //fl.removeAllViews();
            fm.beginTransaction().replace(R.id.content_frame, new MatchDayFragment()).addToBackStack(null).commit();

        } else if (id == R.id.nav_allMatchdays) {
            FrameLayout fl = (FrameLayout) findViewById(R.id.content_frame);
            //fl.removeAllViews();
            fm.beginTransaction().replace(R.id.content_frame, new AllMatchDaysFragment()).addToBackStack(null).commit();

        } else if (id == R.id.nav_table) {
            FrameLayout fl = (FrameLayout) findViewById(R.id.content_frame);
            //fl.removeAllViews();
            fm.beginTransaction().replace(R.id.content_frame, new LeagueTableFragment()).addToBackStack(null).commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
