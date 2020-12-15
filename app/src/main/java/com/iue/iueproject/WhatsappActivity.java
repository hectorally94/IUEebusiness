package com.iue.iueproject;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class WhatsappActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whatsapp);

        Button button = findViewById(R.id.button_send);
        final EditText editText_mobile = findViewById(R.id.edit_mobile_no);
        final EditText editText_msg = findViewById(R.id.edit_message);
        final ImageView imageView = findViewById(R.id.whatsapImage);
        final Context context = this;

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
                    /*
                    String toNumber = "+257548499"; // contains spaces.
                    toNumber = toNumber.replace("+", "").replace(" ", "");
                    File imagefile=new File("android.resource://com.iue.iueproject/drawable/pumbu");
                    Intent sendIntent = new Intent("android.intent.action.MAIN");
                    sendIntent.putExtra(android.content.Intent.EXTRA_STREAM, Uri.parse("https://firebasestorage.googleapis.com/v0/b/ebusiness-d88cc.appspot.com/o/AdiminProductImages%2F1606838007555.jpg?alt=media&token=0b53de67-59c4-45a3-b2d4-80c9953596ca"));
                    sendIntent.putExtra("jid", toNumber + "@s.whatsapp.net");
                    sendIntent.putExtra(android.content.Intent.EXTRA_TEXT, message);
                    sendIntent.setAction(Intent.ACTION_SEND);
                   // sendIntent.setPackage("com.whatsapp");
                    sendIntent.setType("image/png");

                    context.startActivity(sendIntent);
                    */
                    /*
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+"+257"+mobileNumber + "&text="+message));
                     startActivity(intent);*/

                    /*
                    Intent whatsappIntent = new Intent(android.content.Intent.ACTION_SEND);
                    whatsappIntent.setType("image/*");
                    whatsappIntent.putExtra(Intent.EXTRA_TEXT, "Hello World");
                    whatsappIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("content://"+"android.resource://com.iue.iueproject/drawable/pumbu")); //add image path
                    startActivity(Intent.createChooser(whatsappIntent, "Share image using")); */
                    //to get the image from the ImageView (say iv)

                    BitmapDrawable draw = (BitmapDrawable) imageView.getDrawable();
                    Bitmap bitmap = draw.getBitmap();
                   // Bitmap bmp = imageView.getDrawingCache();

                    File sdCard = Environment.getExternalStorageDirectory();
                    File dir = new File(sdCard.getAbsolutePath() + "/YourFolderName");
                    dir.mkdirs();
                    String fileName = String.format("%d.jpg", System.currentTimeMillis());
                    File outFile = new File(dir, fileName);
                    OutputStream outStream = null;
                    try {
                        outStream = new FileOutputStream(outFile);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    try {
                        outStream = new FileOutputStream(outFile);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
                    try {
                        outStream.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        outStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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