package com.iue.iueproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class MyproductListDetail extends AppCompatActivity {
    ImageView imageViewproductlist;
    TextView namelist,pricelist,descriptionlist,quantitelist;

    String namehold;
    String pricehold;
    String descriptionholder;
    String quantiteholder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myproduct_list_detail);

        imageViewproductlist=findViewById(R.id.rImageViewprodlist);
        namelist=findViewById(R.id.idNameprodlist);
        pricelist =findViewById(R.id.idpriceprodlist);
        descriptionlist=findViewById(R.id.iddescriptprodlist);
        quantitelist=findViewById(R.id.idsizeprodlist);


        //usign intents to fetch shared data from previeous calss
        Intent intent=getIntent(); /////initialzing
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

    }
}