package com.fedironics.imeter.imetercustomer;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public boolean isComplaint = false;
    private IntroManager introManager;
    private de.hdodenhof.circleimageview.CircleImageView profilePix;
    private TextView profileName;
    private TextView profileEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        introManager = new IntroManager(this);
        if(!introManager.Check()){
            introManager.setFirst(false);
            Intent i = new Intent(MainActivity.this,WelcomeScreen.class);
            startActivity(i);
            finish();
        }
        if(!isLogged()){
//            Intent intent = new Intent(this,LoginActivity.class);
//            startActivity(intent);
//            finish();
        }
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        profilePix =(de.hdodenhof.circleimageview.CircleImageView) findViewById(R.id.avatar_main);
        profileName = (TextView) findViewById(R.id.profile_name);
        profileEmail = (TextView) findViewById(R.id.profile_email);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isComplaint){
                    return;
                }
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //open the fragment_dashboard fragment

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container,new DashboardFragment()).commit();
        getSupportActionBar().setTitle(R.string.dashboard_title);

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

    public boolean isLogged(){

        //TODO: elaborate this function and make useful
//        SharedPreferences sharedPref =  getSharedPreferences(getResources().getString(R.string.sharedPref), 0);
//        SharedPreferences.Editor editor = sharedPref.edit();
//        if(sharedPref.contains(getResources().getString(R.string.userid_tag))){
//            return true;
//        }
        return false;
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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            logout();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        isComplaint = false;
        int id = item.getItemId();
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (id == R.id.nav_home) {
            fragmentManager.beginTransaction().replace(R.id.fragment_container,new DashboardFragment()).commit();
            getSupportActionBar().setTitle(R.string.dashboard_title);

        } else if (id == R.id.nav_pay) {
            fragmentManager.beginTransaction().replace(R.id.fragment_container,new PaymentFragment()).commit();

            getSupportActionBar().setTitle(R.string.payment_title);
        } else if (id == R.id.nav_blog) {

            fragmentManager.beginTransaction().replace(R.id.fragment_container,new CardContentFragment()).commit();

            getSupportActionBar().setTitle(R.string.blog_title);
        } else if (id == R.id.nav_complain) {
            fragmentManager.beginTransaction().replace(R.id.fragment_container,new ComplaintFragment()).commit();
            isComplaint = true;
            getSupportActionBar().setTitle(R.string.complaint_title);
        } else if (id == R.id.nav_settings) {
            fragmentManager.beginTransaction().replace(R.id.fragment_container,new SettingsFragment()).commit();
            getSupportActionBar().setTitle(R.string.settings_title);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void logout(){
        iMeterApp myApp = (iMeterApp)getApplicationContext();
        myApp.displayNotification("Title Of Notification","Body of Notification",R.drawable.c,"Sticker");
        //TODO : logout and remove preference files
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
