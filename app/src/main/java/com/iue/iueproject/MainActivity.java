package com.iue.iueproject;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<AdminRegclass>listData;
    private RecyclerView rv;
    private MyAdapter adapter;
    ProgressDialog progressDialog ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Assigning Id to ProgressDialog.
        progressDialog = new ProgressDialog(MainActivity.this);
        // Setting progressDialog Title.
        progressDialog.setTitle("Loading...");
        // Showing progressDialog.
        progressDialog.show();
        Toolbar toolbar = findViewById(R.id.toolbarone);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // getSupportActionBar().setDisplayShowTitleEnabled(false);
        ///setting recyclerview
        rv=(RecyclerView)findViewById(R.id.recyclerview);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
       // rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));

        listData=new ArrayList<>();
        final DatabaseReference nm= FirebaseDatabase.getInstance().getReference("AdiminRegistrationData");
        nm.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){


                    for (DataSnapshot npsnapshot : dataSnapshot.getChildren()){
                        AdminRegclass l=npsnapshot.getValue(AdminRegclass.class);
                        //AdminRegclass l=npsnapshot.child("message1").child("title").getValue(AdminRegclass.class);
                        listData.add(l);

                    }
                    adapter=new MyAdapter(getApplicationContext(), listData);
                    rv.setAdapter(adapter);

                    // Hiding the progressDialog after done uploading.
                    progressDialog.dismiss();
                    // Showing toast message after done uploading.
                    Toast.makeText(getApplicationContext(), "Welcome to Net-Shops ", Toast.LENGTH_LONG).show();

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

                Toast.makeText(getApplicationContext(), "issue with firebase database", Toast.LENGTH_LONG).show();

            }


        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        MenuItem searchViewItem = menu.findItem(R.id.actionSearch);
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
