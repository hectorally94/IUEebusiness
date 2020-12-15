package com.iue.iueproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class EditActivity extends AppCompatActivity {
    ImageView imageViewproductlist;
    EditText namelist,pricelist,descriptionlist,quantitelist;
    TextView ki;

    String namehold;
    String pricehold;
    String descriptionholder;
    String quantiteholder;
    String imgholder;
    String myk,myemail;

    Button updates,delete,getupdpicture;

    private addproductclass listdata;
    private DatabaseReference mDatabase;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        imageViewproductlist=findViewById(R.id.idroductimages);
        namelist=findViewById(R.id.idroductname);
        pricelist =findViewById(R.id.idprprice);
        descriptionlist=findViewById(R.id.idDescription);
        quantitelist=findViewById(R.id.idsize);
        updates=findViewById(R.id.updatesbutton);
        delete=findViewById(R.id.deletedbutton);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        //usign intents to fetch shared data from previeous calss
        Intent intent=getIntent(); /////initialzing
        myk= intent.getStringExtra("key");
        myemail=intent.getStringExtra("email");
        namehold= intent.getStringExtra("a");
        pricehold= intent.getStringExtra("b");
        quantiteholder= intent.getStringExtra("c");
        descriptionholder= intent.getStringExtra("d");
        Picasso.get().load(getIntent().getStringExtra("e")).into(imageViewproductlist);
        ///set txt
        pricelist.setText(pricehold);
        namelist.setText(namehold);
        quantitelist.setText(quantiteholder);
        descriptionlist.setText(descriptionholder);
       /// ki.setText(myk);
//////////////////////////////////////////////////////update ////////////////////////////////////////
        updates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(),myk, Toast.LENGTH_SHORT).show();
                UpdateNotes(myk);
            }
        });

        ///////////////////////////////////////////////delete///////////////////////////////////
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),myk,Toast.LENGTH_SHORT).show();
                deleteNote(myk);
            }
        });
    }

    ////////////////////void update fonction////////////////////////////////
    private void UpdateNotes(String id)
    {

        pricehold=pricelist.getText().toString();
        namehold= namelist.getText().toString();
        quantiteholder= quantitelist.getText().toString();
        descriptionholder=descriptionlist.getText().toString();
        String imagestring=Picasso.get().load(getIntent().getStringExtra("e")).toString();

        addproductclass listdata = new addproductclass(id,myemail,imagestring,namehold, pricehold, quantiteholder,descriptionholder);
        mDatabase.child("AdiminProducts").child(id).setValue(listdata).
                addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(EditActivity.this, " Updated", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),AdminDrower.class));
                    }
                });

    }
///////////////////////// void delete foction//////////////////////////////////////:
    private void deleteNote(String id) {
        mDatabase.child("AdiminProducts").child(id).removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(EditActivity.this," deleted",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),AdminDrower.class));

                    }
                });
    }
}