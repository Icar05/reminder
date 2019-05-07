package com.example.icar.my_notification;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;

import com.example.icar.my_notification.fragments.AboutFragment;
import com.example.icar.my_notification.fragments.AllLIstFragment;
import com.example.icar.my_notification.fragments.CreateFragment;
import com.example.icar.my_notification.fragments.ImportantListFragment;
import com.example.icar.my_notification.fragments.NotImportantListFragment;
import com.example.icar.my_notification.fragments.ViewFragment;
import com.example.icar.my_notification.helpers.Constants;
import com.example.icar.my_notification.helpers.CustomSpannable;
import com.example.icar.my_notification.service.InvoceService;



public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {




    DrawerLayout drawer;
    public Toolbar toolbar;
    FragmentManager fragmentManager;
    NavigationView navigationView;
    public FloatingActionButton fab;
    String id = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        init();
        id = getIntent().getStringExtra("id");

        Log.d("bug", "id in main ->  "+id);

        if(savedInstanceState == null){
            setFirstSelectedItem();
            startService(new Intent(getApplicationContext(), InvoceService.class));
        }



    }









    private void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

         navigationView = (NavigationView) findViewById(R.id.nav_view);
         navigationView.setNavigationItemSelectedListener(this);
        customizeNavMenu(navigationView);
        
        fragmentManager = getSupportFragmentManager();

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabOnClick();
            }
        });


    }

    private void customizeNavMenu(NavigationView navigationView) {

        navigationView.setItemIconTintList(null);


        Menu m = navigationView.getMenu();
        for (int i=0;i<m.size();i++) {
            MenuItem mi = m.getItem(i);

            //for aapplying a font to subMenu ...
            SubMenu subMenu = mi.getSubMenu();
            if (subMenu!=null && subMenu.size() >0 ) {
                for (int j=0; j <subMenu.size();j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    applyFontToMenuItem(subMenuItem);
                }
            }

            //the method we have create in activity
            applyFontToMenuItem(mi);
        }
    }

    private void applyFontToMenuItem(MenuItem subMenuItem) {
        Typeface font = Typeface.createFromAsset(getAssets(), Constants.CUSTOM_FONT);
        SpannableString mNewTitle = new SpannableString(subMenuItem.getTitle());
        mNewTitle.setSpan(new CustomSpannable("" , font), 0 , mNewTitle.length(),  Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        subMenuItem.setTitle(mNewTitle);
    }


    @Override
    public void onBackPressed() {

        if (this.drawer.isDrawerOpen(GravityCompat.START)) {
            this.drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        Fragment fragment = null;

        switch (id)
        {
            case R.id.nav_info:  fragment = AboutFragment.newInstance(); break;

            case R.id.nav_create:fragment = CreateFragment.newInstance(); break;

            case R.id.nav_all:    fragment = AllLIstFragment.newInstance();break;

            case R.id.nav_urgent: fragment = ImportantListFragment.newInstance();break;

            case R.id.nav_not_urgent: fragment = NotImportantListFragment.newInstance(); break;

            case R.id.nav_exit :    clearBackStack();
                                     finish();
                                      break;
        }

            if (fragment != null) {
                goTo(fragment);
            }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void setFirstSelectedItem() {


        if(id == null){


            fragmentManager.beginTransaction()
                    .replace(R.id.contentMain, AllLIstFragment.newInstance())
                    .commit();
        }else{
            fragmentManager.beginTransaction()
                    .replace(R.id.contentMain, ViewFragment.newInstance(id))
                    .commit();
        }


    }


    private void fabOnClick() {
        goTo(CreateFragment.newInstance());
        navigationView.getMenu().getItem(1).setChecked(true);
    }


    private void clearBackStack(){
        FragmentManager fm = getSupportFragmentManager();
        for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
    }


    private void goTo(Fragment fragment){
        fragmentManager.beginTransaction()
                .replace(R.id.contentMain, fragment)
                .addToBackStack("myStack")
                .commit();
    }
}
