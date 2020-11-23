package com.iue.iueproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Aftersplash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aftersplash);

    }
    public void onClickSingnin(View v){

        Intent intent = new Intent(Aftersplash.this, loginActivity.class);
        startActivity(intent);
    }
    public void onClickSingUp(View v){

        Intent intent = new Intent(Aftersplash.this, AdminRegistration.class);
        startActivity(intent);
    }
    public void onClickforget(View v){

        Intent intent = new Intent(Aftersplash.this, Forgetpassword.class);
        startActivity(intent);
    }
    public void EXIT(View v){

        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

}