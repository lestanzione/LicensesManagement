package com.stanzione.licensesmanagement.ui;

import android.content.res.Configuration;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.stanzione.licensesmanagement.R;
import com.stanzione.licensesmanagement.model.UserAccess;
import android.support.v7.widget.*;

public class MainActivity extends AppCompatActivity implements
        CompanyListFragment.OnFragmentInteractionListener,
        ProjectListFragment.OnFragmentInteractionListener,
        SoftwareListFragment.OnFragmentInteractionListener,
        ContactListFragment.OnFragmentInteractionListener,
        DrawerRecyclerAdapter.OnDrawerItemListener{

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
	private Toolbar toolbar;

    private UserAccess loggedUser;

    //TODO: choose between NavigationView or RecyclerView
    //private NavigationView drawerNavigationView;
    private RecyclerView drawerRecyclerView;
    private ArrayAdapter<String> drawerAdapter;

    private static final String TITLES[] = {"Companies", "Projects", "Softwares", "Contacts"};
    private static final int ICONS[] = {R.drawable.ic_drawer_company,
            android.R.drawable.ic_delete,
            android.R.drawable.ic_delete,
            android.R.drawable.ic_delete,
            android.R.drawable.ic_delete};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loggedUser = (UserAccess) getIntent().getSerializableExtra("loggedUser");

        setupDrawer();

    }

    private void setupDrawer(){

		toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setTitle("Licenses Management");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        //TODO: choose between NavigationView or RecyclerView
        //drawerNavigationView = (ListView)findViewById(R.id.navDrawer);
        drawerRecyclerView = (RecyclerView) findViewById(R.id.navDrawer);
        drawerRecyclerView.setHasFixedSize(true);
        drawerRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        addDrawerItems();

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);

    }

    private void addDrawerItems() {
        //String[] osArray = { "Companies", "Projects", "Softwares", "Contacts"};
        //drawerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
        //drawerList.setAdapter(drawerAdapter);
        drawerRecyclerView.setAdapter(
                new DrawerRecyclerAdapter(
                        this,
                        TITLES,
                        ICONS,
                        loggedUser.getFirstName() + " " + loggedUser.getLastName(),
                        loggedUser.getEmail(),
                        android.R.drawable.sym_def_app_icon
                )
        );

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onDrawerItemSelected(View view) {
        int itemPosition = drawerRecyclerView.getChildLayoutPosition(view);

        if (itemPosition == 1) {
            CompanyListFragment companyListFragment = CompanyListFragment.newInstance(loggedUser);

			getSupportActionBar().setTitle("Companies");
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.mainBody, companyListFragment).commit();
        }
        else if (itemPosition == 2) {
            ProjectListFragment projectListFragment = ProjectListFragment.newInstance(loggedUser);

			getSupportActionBar().setTitle("Projects");
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.mainBody, projectListFragment).commit();
        }
        else if (itemPosition == 3) {
            SoftwareListFragment softwareListFragment = SoftwareListFragment.newInstance(loggedUser);

			getSupportActionBar().setTitle("Softwares");
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.mainBody, softwareListFragment).commit();
        }
        else if (itemPosition == 4) {
            ContactListFragment contactListFragment = ContactListFragment.newInstance(loggedUser);

			getSupportActionBar().setTitle("Contacts");
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.mainBody, contactListFragment).commit();
        }

        mDrawerLayout.closeDrawers();

    }

}
