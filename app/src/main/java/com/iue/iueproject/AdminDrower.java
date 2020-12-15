package com.iue.iueproject;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminDrower extends AppCompatActivity {
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;
    private TextView eTextEmail;
    private NavigationView navigationView;
///
private List<addproductclass> listData;
    private RecyclerView rv;
    private MyproductAdapter adapter;
    ////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_drower);
        // loading
        rv=(RecyclerView)findViewById(R.id.recyclerviewproduct);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));

        // Set a Toolbar to replace the ActionBar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView textViewtoo = (TextView)toolbar.findViewById(R.id.toolbarTextView);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //textViewtoo.setText("Dashbord");

        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = setupDrawerToggle();
        // Setup toggle to display hamburger icon with nice animation
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();
        // Tie DrawerLayout events to the ActionBarToggle
        mDrawer.addDrawerListener(drawerToggle);

        // Find our drawer view
        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        // Lookup navigation view
        NavigationView navigationView = (NavigationView) findViewById(R.id.nvView);
// Inflate the header view at runtime
        View headerLayout = navigationView.inflateHeaderView(R.layout.nav_header);
// We can now look up items within the header if needed
       ImageView ivHeaderPhoto = headerLayout.findViewById(R.id.vimageView);
       eTextEmail = headerLayout.findViewById(R.id.TextEmail);

        // create the get Intent object
        Intent intent = getIntent();

        // receive the value by getStringExtra() method
        // and key must be same which is send by first activity
        String str = intent.getStringExtra("message_key");

        // display the string into textView
        eTextEmail.setText(str);
        textViewtoo.setText(str);

        // Setup drawer view
        setupDrawerContent(nvDrawer);
        /// populating data to the recyclerview
        listData=new ArrayList<>();
        final DatabaseReference nm= FirebaseDatabase.getInstance().getReference();
        Query Q=nm.child("AdiminProducts").orderByChild("email").equalTo(str);
        Q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    listData.clear();
                    for (DataSnapshot npsnapshot : dataSnapshot.getChildren()){
                        addproductclass l=npsnapshot.getValue(addproductclass.class);
                        //AdminRegclass l=npsnapshot.child("message1").child("title").getValue(AdminRegclass.class);
                        listData.add(l);
                    }
                   adapter=new MyproductAdapter(getApplicationContext(), listData);
                   rv.setAdapter(adapter);

                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private ActionBarDrawerToggle setupDrawerToggle() {

        // NOTE: Make sure you pass in a valid toolbar reference.  ActionBarDrawToggle() does not require it

        // and will not render the hamburger icon without it.

        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open,  R.string.drawer_close);

    }

    private void setupDrawerContent(NavigationView navigationView) {

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {

                    @Override

                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {

        switch(menuItem.getItemId()) {
            case R.id.activity_addproduct:

                // get the value which input by user in EditText
                // and convert it to string
                String strEmail = eTextEmail.getText().toString();
                // Create the Intent object of this class Context() to Second_activity class
                Intent intent = new Intent(getApplicationContext(), Addproduct.class);

                // now by putExtra method put the value in key, value pair
                // key is message_key by this key we will receive the value, and put the string

                intent.putExtra("Email_key", strEmail);

                // start the Intent
                startActivity(intent);
               // Intent intent = new Intent(AdminDrower.this, Addproduct.class);

               // startActivity(intent);
                break;
            case R.id.activity_favry_product:
                Intent intento = new Intent(AdminDrower.this, FavryProduct.class);
                startActivity(intento);
                break;
            case R.id.activity_shopping_cart:
                Intent intentv = new Intent(AdminDrower.this, shoppingCart.class);
                startActivity(intentv);
                break;
            default:

                Toast.makeText(AdminDrower.this,"No Activity selected",Toast.LENGTH_LONG).show();

        }
  // Highlight the selected item has been done by NavigationView

        menuItem.setChecked(true);

        // Set action bar title

        setTitle(menuItem.getTitle());

        // Close the navigation drawer

        mDrawer.closeDrawers();

    }


    @Override

    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {

            return true;
        }
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
}
// `onPostCreate` called when activity start-up is complete after `onStart()`

    // NOTE 1: Make sure to override the method with only a single `Bundle` argument

    // Note 2: Make sure you implement the correct `onPostCreate(Bundle savedInstanceState)` method.

    // There are 2 signatures and only `onPostCreate(Bundle state)` shows the hamburger icon.

    @Override

    protected void onPostCreate(Bundle savedInstanceState) {

        super.onPostCreate(savedInstanceState);

        // Sync the toggle state after onRestoreInstanceState has occurred.

        drawerToggle.syncState();

    }



    @Override

    public void onConfigurationChanged(Configuration newConfig) {

        super.onConfigurationChanged(newConfig);

        // Pass any configuration change to the drawer toggles

        drawerToggle.onConfigurationChanged(newConfig);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.drowadminsearch, menu);
        MenuItem searchViewItem = menu.findItem(R.id.actionSearchlistadmin);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchViewItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
             /*   if(list.contains(query)){
                    adapter.getFilter().filter(query);
                }else{
                    Toast.makeText(MainActivity.this, "No Match found",Toast.LENGTH_LONG).show();
                }*/
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

}