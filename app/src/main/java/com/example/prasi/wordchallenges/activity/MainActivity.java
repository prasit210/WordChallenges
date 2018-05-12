package com.example.prasi.wordchallenges.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.localizationactivity.ui.LocalizationActivity;
import com.bumptech.glide.Glide;
import com.example.prasi.wordchallenges.R;
import com.example.prasi.wordchallenges.fragment.AddFragment.AddWordFragment;
import com.example.prasi.wordchallenges.fragment.AddFragment.AddWordPagerFragment;
import com.example.prasi.wordchallenges.fragment.GGWP;
import com.example.prasi.wordchallenges.fragment.checkword.WordHistory;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;

public class MainActivity extends LocalizationActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private SharedPreferences sharedPreferences;
    private ImageView imgNavHeader;
    private TextView txtName,txtEmail;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        setDefaultLanguage("en");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null){
            sharedPreferences = getSharedPreferences("LOGIN", Context.MODE_PRIVATE);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.containerMain,
                            new AddWordPagerFragment())
                    .commit();
        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View view = navigationView.getHeaderView(0);
        imgNavHeader = (ImageView)view.findViewById(R.id.imgNavHead);
        txtName = (TextView)view.findViewById(R.id.txtNameNav);
        txtEmail = (TextView)view.findViewById(R.id.txtNavEmail);
        setNavbarHeader();
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void setNavbarHeader() {
        if (sharedPreferences.getString("Type","").equals("Facebook")){

            Glide.with(this).load("https://graph.facebook.com/" +
                    sharedPreferences.getString("ID","") + "/picture?type=large")
                    .into(imgNavHeader);

            txtName.setText(sharedPreferences.getString("Name","").toString()+" "+
                    sharedPreferences.getString("Surname","").toString());
            txtEmail.setText(sharedPreferences.getString("Email","").toString());
        }else {
            //Glide.with(this).load("https://graph.facebook.com/" + sharedPreferences.getString("ID","") + "/picture?type=large").into(imgNavHeader);
            imgNavHeader.setImageResource(R.drawable.ic_launcher);

            txtName.setText(sharedPreferences.getString("Name","").toString()+" "+
                    sharedPreferences.getString("Surname","").toString());

            txtEmail.setText(sharedPreferences.getString("Email","").toString());
        }

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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_addword) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.containerMain,
                            new AddWordPagerFragment())
                    .commit();
        } else if (id == R.id.nav_checkword) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.containerMain,
                            new WordHistory())
                    .commit();
        } else if (id == R.id.nav_example) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.containerMain,
                            new GGWP())
                    .commit();
        } else if (id == R.id.nav_achivement) {

        } else if (id == R.id.nav_setting) {
            Intent intent = new Intent(getApplicationContext(),SettingActivity.class);
            startActivity(intent);
        }else if (id == R.id.nav_support){

        }
        else if (id == R.id.nav_logout) {
            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            sharedPreferences.edit().clear().commit();
            LoginManager.getInstance().logOut();
            AccessToken.setCurrentAccessToken(null);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
