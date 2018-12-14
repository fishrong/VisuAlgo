package cn.wangsr.algorithms;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Fragment f1,f2,f3,f4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        f1 = new SortingFragment();
        f2 = new TreeFragment();
        f3 = new NetFragment();
        f4 = new AboutFragment();



    }


    public void launchSelectionActivity(View view) {
        Intent intent = new Intent(MainActivity.this,SelectionActivity.class);
        startActivity(intent);
    }
    public void launchInsertionActivity(View view) {
        Intent intent = new Intent(MainActivity.this,InsertionActivity.class);
        startActivity(intent);
    }
    public void launchShellActivity(View view) {
        Intent intent = new Intent(MainActivity.this,ShellActivity.class);
        startActivity(intent);
    }
    public void launchMergeActivity(View view) {
        Intent intent = new Intent(MainActivity.this,MergeActivity.class);
        startActivity(intent);
    }
    public void launchQuickActivity(View view) {
        Intent intent = new Intent(MainActivity.this,QuickActivity.class);
        startActivity(intent);
    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (id == R.id.nav_sort) {
            ft.replace(R.id.myFragment,f1);
        } else if (id == R.id.nav_map) {
            ft.replace(R.id.myFragment,f2);
        } else if (id == R.id.nav_graph) {
            ft.replace(R.id.myFragment,f3);
        }
        else if (id == R.id.nav_about) {
            ft.replace(R.id.myFragment,f4);
        }
        ft.commitAllowingStateLoss();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



}
