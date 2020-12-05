package com.iue.iueproject;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class WhatsappActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whatsapp);

        Button button = findViewById(R.id.button_send);
        final EditText editText_mobile = findViewById(R.id.edit_mobile_no);
        final EditText editText_msg = findViewById(R.id.edit_message);
        final ImageView imagewhatsab = findViewById(R.id.whatsapImage);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //So you can get the edittext value
                String mobileNumber = editText_mobile.getText().toString();
                String message = editText_msg.getText().toString();
                    ////////////////
               // Uri uri = Uri.parse("android.resource://com.iue.iueproject/drawable/pumbu");
              //  Intent sharingIntent = new Intent();
              //  sharingIntent.setType("image/jpg");
               // sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);
              //  startActivity(Intent.createChooser(sharingIntent, "Share image using"));
                ////////////////////////
                boolean installed = appInstalledOrNot("com.whatsapp");
                boolean installedGb = appInstalledOrNot("com.gbwhatsapp");
                if (installed){
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    //intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+"+257"+mobileNumber + "&text="+message));
                   // startActivity(intent);
                    Uri uri = Uri.parse("android.resource://com.iue.iueproject/drawable/pumbu");
                   // Intent sharingIntent = new Intent();
                    intent.setType("image/jpg");
                    intent.putExtra(Intent.EXTRA_STREAM, uri);
                    startActivity(Intent.createChooser(intent, "Share image using"));

                    /*
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+"+257"+mobileNumber + "&text="+message));
                    startActivity(intent);
                    */

                }else if(installedGb){


                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    //intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+"+257"+mobileNumber + "&text="+message));
                    // startActivity(intent);
                    Uri uri = Uri.parse("android.resource://com.iue.iueproject/drawable/pumbu");
                    // Intent sharingIntent = new Intent();
                    intent.setType("image/jpg");
                    intent.putExtra(Intent.EXTRA_STREAM, uri);
                    startActivity(Intent.createChooser(intent, "Share image using"));

                }
                else {
                    Toast.makeText(WhatsappActivity.this, "Whats app not installed on your device", Toast.LENGTH_SHORT).show();
                }

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