package com.iue.iueproject;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class AdminRegistration extends AppCompatActivity {
    // Folder path for Firebase Storage.
    String Storage_Path = "All_Image_Uploads/";

    // Root Database Name for Firebase Database.
    String Database_Path = "All_Image_Uploads_Database";

    // Creating button.
    Button ChooseButton, UploadButton;

    // Creating ImageView.
    ImageView USerimageURL;
    EditText editUserName ;
    EditText editemail ;
    EditText editpassword ;
    EditText edittelephonelumicash ;
    EditText editTelephoneecocash ;
    EditText editnefID ;
    EditText editIDpurmission ;
    EditText editBusinessName ;

    // Creating URI.
    Uri FilePathUri;
    // Creating StorageReference and DatabaseReference object.
    StorageReference storageReference;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    // Image request code for onActivityResult() .
    int Image_Request_Code = 7;

    ProgressDialog progressDialog ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_registration);
            //trying to unitialise the email and password
        firebaseAuth=FirebaseAuth.getInstance();
        // Assign FirebaseStorage instance to storageReference.
        storageReference = FirebaseStorage.getInstance().getReference();

        // Assign FirebaseDatabase instance with root database name.
        databaseReference = FirebaseDatabase.getInstance().getReference(Database_Path);

        //Assign ID'S to button.
        ChooseButton = (Button)findViewById(R.id.ButtonChooseImage);
        UploadButton = (Button)findViewById(R.id.ButtonUploadImage);

        // Assign ID's to EditText.
        editBusinessName = (EditText)findViewById(R.id.BusinessName); // Assign ID's to EditText.
        editIDpurmission = (EditText)findViewById(R.id.IDpurmission); // Assign ID's to EditText.
        editnefID = (EditText)findViewById(R.id.nefID); // Assign ID's to EditText.
        editTelephoneecocash = (EditText)findViewById(R.id.Telephoneecocash); // Assign ID's to EditText.
        edittelephonelumicash = (EditText)findViewById(R.id.telephonelumicash); // Assign ID's to EditText.
        editpassword = (EditText)findViewById(R.id.password); // Assign ID's to EditText.
        editemail = (EditText)findViewById(R.id.email); // Assign ID's to EditText.
        editUserName = (EditText)findViewById(R.id.UserName);

        // Assign ID'S to image view.
        USerimageURL = (ImageView)findViewById(R.id.USerimageURL);

        // Assigning Id to ProgressDialog.
        progressDialog = new ProgressDialog(AdminRegistration.this);

        // Adding click listener to Choose image button.
        ChooseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Creating intent.
                Intent intent = new Intent();

                // Setting intent type as image to select image from phone storage.
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Please Select Image"), Image_Request_Code);

            }
        });

        // Adding click listener to Upload image button.
        UploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Calling method to upload selected image on Firebase storage.
                SaveAdminRegistrationData();

            }

           
        });



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {

            FilePathUri = data.getData();

            try {

                // Getting selected image into Bitmap.
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), FilePathUri);

                // Setting up bitmap selected image into ImageView.
                USerimageURL.setImageBitmap(bitmap);

                // After selecting image change choose button above text.
                ChooseButton.setText("Logo Selected");

            }
            catch (IOException e) {

                e.printStackTrace();
            }
        }
    }
    // Creating Method to get the selected image file Extension from File Path URI.
    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();

        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        // Returning the file Extension.
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ;

    }

    private void SaveAdminRegistrationData() {
        //getting email and password from edit texts
        String Athemail = editemail.getText().toString().trim();
        String Athpassword = editpassword.getText().toString().trim();

        firebaseAuth.createUserWithEmailAndPassword(Athemail, Athpassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if(task.isSuccessful()){
                            //display some message here
                            Toast.makeText(AdminRegistration.this,"firebaseAuth registered",Toast.LENGTH_LONG).show();
                        }else{
                            //display some message here
                            Toast.makeText(AdminRegistration.this,"firebaseAuth Error",Toast.LENGTH_LONG).show();
                        }

                    }
                });

        // Checking whether FilePathUri Is empty or not.
        if (FilePathUri != null) {

            // Setting progressDialog Title.
            progressDialog.setTitle("Saving...");

            // Showing progressDialog.
            progressDialog.show();

            // Creating second StorageReference.
            StorageReference storageReference2nd = storageReference.child(Storage_Path + System.currentTimeMillis() + "." + GetFileExtension(FilePathUri));

            // Adding addOnSuccessListener to second StorageReference.
            
            storageReference2nd.putFile(FilePathUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                            // Getting image name from EditText and store into string variable.
                            String TempBusinessName = editBusinessName.getText().toString().trim();
                            String TempUserName = editUserName.getText().toString().trim();
                            String Tempemail = editemail.getText().toString().trim();
                            String Temppassword = editpassword.getText().toString().trim();
                            String Telephoners = edittelephonelumicash.getText().toString().trim();
                            String TempTelephoneecocash = editTelephoneecocash.getText().toString().trim();
                            String TempnefID = editnefID.getText().toString().trim();
                            String TempIDpurmission = editIDpurmission.getText().toString().trim();

                            // Hiding the progressDialog after done uploading.

                            progressDialog.dismiss();

                            // Showing toast message after done uploading.
                            Toast.makeText(getApplicationContext(), " Uploaded Successfully ", Toast.LENGTH_LONG).show();

                            @SuppressWarnings("VisibleForTests")
                            AdminRegclass AdminRegclobjc = new AdminRegclass(
                                    taskSnapshot.getMetadata().getReference().getDownloadUrl().toString(),
                                    TempBusinessName,
                                    TempUserName,
                                    Tempemail,
                                    Temppassword,
                                    Telephoners,
                                    TempTelephoneecocash,
                                    TempnefID,
                                    TempIDpurmission);

                            // Getting image upload ID.
                            String ImageAdminId = databaseReference.push().getKey();

                            // Adding image upload id s child element into databaseReference.
                            databaseReference.child(ImageAdminId).setValue(AdminRegclobjc);
                        }


                    })
                    // If something goes wrong .
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {

                            // Hiding the progressDialog.
                            progressDialog.dismiss();

                            // Showing exception erro message.
                            Toast.makeText(AdminRegistration.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })

                    // On progress change upload time.
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                            // Setting progressDialog Title.
                            progressDialog.setTitle(" Uploading...");

                        }
                    });
        }
        else {

            Toast.makeText(AdminRegistration.this, "Please Select Image or Add Image Name", Toast.LENGTH_LONG).show();

        }

    }
}