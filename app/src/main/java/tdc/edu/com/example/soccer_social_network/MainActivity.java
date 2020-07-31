package tdc.edu.com.example.soccer_social_network;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.view.Menu;

import tdc.edu.com.example.soccer_social_network.Database.SQLiteHelper;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static SQLiteHelper sqLiteHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        sqLiteHelper = new SQLiteHelper( this );
        sqLiteHelper.queryData( "CREATE TABLE IF NOT EXISTS TEAM (Id INTEGER PRIMARY KEY AUTOINCREMENT, tendoi VARCHAR, diachi VARCHAR, image BLOB)" );
        sqLiteHelper.queryData( "CREATE TABLE IF NOT EXISTS MEMBER (Id INTEGER PRIMARY KEY AUTOINCREMENT, tenmember VARCHAR, vitri VARCHAR, image BLOB)" );



        Toolbar toolbar = findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
        FloatingActionButton fab = findViewById( R.id.fab );
        fab.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make( view, "Replace with your own action", Snackbar.LENGTH_LONG )
                        .setAction( "Action", null ).show();
            }
        } );
        DrawerLayout drawer = findViewById( R.id.drawer_layout );
        NavigationView navigationView = findViewById( R.id.nav_view );
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close );
        drawer.addDrawerListener( toggle );
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener( this );


        setTitle("Menu");
        MenuChinh fragment = new MenuChinh();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment, "Home");
        fragmentTransaction.commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById( R.id.drawer_layout );
        if (drawer.isDrawerOpen( GravityCompat.START )) {
            drawer.closeDrawer( GravityCompat.START );
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate( R.menu.main, menu );
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

        return super.onOptionsItemSelected( item );
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            setTitle("Home");
            Home fragment = new Home();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, fragment, "Home");
            fragmentTransaction.commit();
            // Handle the camera action
        } else if (id == R.id.nav_addTeam) {

            startActivity( new Intent( MainActivity.this,AddTeam.class ) );


        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_update) {

            startActivity( new Intent( MainActivity.this,TeamList.class ) );

        } else if (id == R.id.nav_login) {

        } else if (id == R.id.nav_logout) {

        }

        DrawerLayout drawer = findViewById( R.id.drawer_layout );
        drawer.closeDrawer( GravityCompat.START );
        return true;
    }
}
