package com.iue.iueproject;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class MyproductListDetail extends AppCompatActivity {
    ImageView imageViewproductlist;
    TextView namelist,pricelist,descriptionlist,quantitelist;
    EditText numb;

    String namehold;
    String pricehold;
    String descriptionholder;
    String quantiteholder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myproduct_list_detail);

        //// unitialising value to connect to whatsapp
        Button button = findViewById(R.id.button_send);
        final EditText editText_mobile = findViewById(R.id.edit_mobile_no);
        final EditText editText_msg = findViewById(R.id.edit_message);
        final ImageView imageView = findViewById(R.id.whatsapImage);
        /////// button to click on image and ispand it


        ///////////button to connect to whatsapp

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //So you can get the edittext value
                String mobileNumber = editText_mobile.getText().toString();
                String message = editText_msg.getText().toString();
                   boolean installed = appInstalledOrNot("com.whatsapp");
                boolean installedGb = appInstalledOrNot("com.gbwhatsapp");
                if (installed){
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+"+257"+mobileNumber + "&text="+message));
                    startActivity(intent);

                }else if(installedGb){
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+"+257"+mobileNumber + "&text="+message));
                    startActivity(intent);
                }
                else {
                    Toast.makeText(MyproductListDetail.this, "Whats app not installed on your device", Toast.LENGTH_SHORT).show();
                }
            }
        });

        imageViewproductlist=findViewById(R.id.rImageViewprodlist);
        namelist=findViewById(R.id.idNameprodlist);
        pricelist =findViewById(R.id.idpriceprodlist);
        descriptionlist=findViewById(R.id.iddescriptprodlist);
        quantitelist=findViewById(R.id.idsizeprodlist);


        //usign intents to fetch shared data from previeous calss
        Intent intent=getIntent(); /////initialzing
        String myemail = intent.getStringExtra("email");
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
        ////////////fech data from firebase
        final DatabaseReference nm= FirebaseDatabase.getInstance().getReference();
        Query Q=nm.child("AdiminRegistrationData").orderByChild("email").equalTo(myemail);
        Q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for(DataSnapshot ds : dataSnapshot.getChildren()) {
                        String telephonelumicash = ds.child("telephonelumicash").getValue(String.class);
                       /// String telephoneecocash = ds.child("telephoneecocash").getValue(String.class);
                        ///Log.d("TAG", telephonelumicash + " / " + telephoneecocash);
                        editText_mobile.setText(telephonelumicash);
                    }

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    //Create method appInstalledOrNot
    private boolean appInstalledOrNot(String url){
        PackageManager packageManager =getPackageManager();
        boolean app_installed;
        try {
            packageManager.getPackageInfo(url,PackageManager.GET_ACTIVITIES);
            app_installed = true;
        }catch (PackageManager.NameNotFoundException e){
            app_installed = false;
        }
        return app_installed;
    }

}