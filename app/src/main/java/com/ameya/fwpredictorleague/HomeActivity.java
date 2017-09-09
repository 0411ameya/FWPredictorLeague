package com.ameya.fwpredictorleague;

import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import fragments.AllMatchDaysFragment;
import fragments.LeagueTableFragment;
import fragments.MatchDayFragment;
import fragments.MyFwplFragment;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public OkHttpClient okHttpClient;
    public Request request;
    public Profile profile = Profile.getInstance();


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
            fm.beginTransaction().replace(R.id.content_frame, new MatchDayFragment());

        } else if (id == R.id.nav_allMatchdays) {
            FrameLayout fl = (FrameLayout) findViewById(R.id.content_frame);
            //fl.removeAllViews();
            fm.beginTransaction().replace(R.id.content_frame, new AllMatchDaysFragment());

        } else if (id == R.id.nav_table) {
            FrameLayout fl = (FrameLayout) findViewById(R.id.content_frame);
            //fl.removeAllViews();
            fm.beginTransaction().replace(R.id.content_frame, new LeagueTableFragment());

        } else if (id == R.id.nav_signout) {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    HomeActivity.this);

            // set title
            alertDialogBuilder.setTitle("Sign Out");

            // set dialog message
            alertDialogBuilder
                    .setMessage("Would you like to Sign Out " + profile.getFirst_name() + " ?")
                    .setCancelable(false)
                    .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            // if this button is clicked, close
                            // current activity
                            profile.resetInstance();
                            HomeActivity.this.finish();
                            Intent i = new Intent(HomeActivity.this, FwLoginActivity.class);
                            startActivity(i);
                        }
                    })
                    .setNegativeButton("No, I love FWPL!",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            // if this button is clicked, just close
                            // the dialog box and do nothing
                            dialog.cancel();
                        }
                    });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();

            //FrameLayout fl = (FrameLayout) findViewById(R.id.content_frame);
            //fl.removeAllViews();
            //fm.beginTransaction().replace(R.id.content_frame, new SignOutFragment());

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    public void setOkHttpClient(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }
}
