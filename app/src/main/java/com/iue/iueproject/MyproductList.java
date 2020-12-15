package com.iue.iueproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MyproductList extends AppCompatActivity {
    ImageView imageViewAdmin;
    TextView CompanyName;

    //variable to hold shared information
    String emailholder;
    String CompanyNameholder;
    ///
    private List<addproductclass> listData;

    private RecyclerView rv;
    private MyproductAdapter adapter;
    ////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myproduct_list);
        Toolbar toolbar = findViewById(R.id.toolbartwo);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        imageViewAdmin=findViewById(R.id.toolBarPicture);
        CompanyName=findViewById(R.id.toolbarCompanyName);
        // loading to a recyclerviw content
        rv=(RecyclerView)findViewById(R.id.recyclerviewproductList);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));

        //usign intents to fetch shared data from previeous calss
        Intent intent=getIntent(); /////initialzing
        CompanyNameholder= intent.getStringExtra("a");
        Picasso.get().load(getIntent().getStringExtra("b")).into(imageViewAdmin);
        String Email=intent.getStringExtra("c");
        ///set txt
        CompanyName.setText(CompanyNameholder);


        /// populating data to the recyclerview
        listData=new ArrayList<>();
        final DatabaseReference nm= FirebaseDatabase.getInstance().getReference();
        Query Q=nm.child("AdiminProducts").orderByChild("email").equalTo(Email);
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menuproductlist, menu);
        MenuItem searchViewItem = menu.findItem(R.id.actionSearchlist);
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