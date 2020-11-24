package com.iue.iueproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class loginActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonSignup;
    private ProgressDialog progressDialog;
    //defining firebaseauth object
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth=FirebaseAuth.getInstance();
        //initializing views
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        buttonSignup = (Button) findViewById(R.id.selleronclick);

        progressDialog = new ProgressDialog(this);

        //attaching listener to button
        buttonSignup.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //calling register method on click
        SignIn();
    }
    private void SignIn(){

        //getting email and password from edit texts
        String email = editTextEmail.getText().toString().trim();
        String password  = editTextPassword.getText().toString().trim();

        //checking if email and passwords are empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }

        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage(" Please Wait...");
        progressDialog.show();


        //creating a new user
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(loginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if(task.isSuccessful()){
                            //display some message here
                            Toast.makeText(loginActivity.this,"Successfully ",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(loginActivity.this, Dashboardseller  .class);
                            startActivity(intent);
                        }else{
                            //display some message here
                            Toast.makeText(loginActivity.this," Error",Toast.LENGTH_LONG).show();
                            //dictate where to move next

                        }
                        progressDialog.dismiss();
                    }
                });

    }

    public  void useronclick(View v){
        Intent intent = new Intent(loginActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public  void selleronclick(View v){
        Intent intent = new Intent(loginActivity.this, Dashboardseller.class);
        startActivity(intent);
    }
}