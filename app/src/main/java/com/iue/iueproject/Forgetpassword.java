package com.iue.iueproject;
//

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Forgetpassword extends AppCompatActivity {
    //declare elements

    private EditText forgotEmail;
    Button resetEmail;
    ProgressBar progressBar;
    //declare the firebase
    FirebaseAuth auth;
    //progressBar

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);
        //intialize auth
        auth = FirebaseAuth.getInstance();
        //ref our views
        forgotEmail = findViewById(R.id.editTextEmail);
        resetEmail = findViewById(R.id.buttonreset);
        progressBar = findViewById(R.id.progressBar);

        resetEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgotPassword();
            }
        });


    }
    //method to run when user wants to reset the password
    private void forgotPassword() {
        //pick users input
        String forgot_email = forgotEmail.getText().toString().trim();
        //check
        if (TextUtils.isEmpty(forgot_email)){
            Toast.makeText(this, "Email Field cannot be empty", Toast.LENGTH_SHORT).show();
        } else {
            run(forgot_email);
        }


    }

    private void run(String forgot_email) {
        progressBar.setVisibility(View.VISIBLE);
       
        //method to reset a user password in firebase is sendPasswordResetEmail()
        auth.sendPasswordResetEmail(forgot_email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(Forgetpassword.this, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Forgetpassword.this, "Failed to send reset email! Email may not be valid", Toast.LENGTH_SHORT).show();
                }

                progressBar.setVisibility(View.GONE);
            }
        });
    }

    //back 2 login
    public void back2Login(View v){
        Intent intent = new Intent(Forgetpassword.this,loginActivity.class);
        startActivity(intent);
    }
}
