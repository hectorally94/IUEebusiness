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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class Addproduct extends AppCompatActivity {
    private Toolbar toolbar;
    String Storage_Path = "AdiminProductImages/";
    // Root Database Name for Firebase Database.
    String Database_Path = "AdiminProducts";

    // Creating button.
    Button ChooseButton, UploadButton;

    // Creating ImageView.
    ImageView productimage;
    EditText productname ;
    EditText productprice ;
    EditText productsize ;
    EditText productdescription ;
    TextView textViewtoo;

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
        setContentView(R.layout.activity_addproduct);
        // Assign FirebaseStorage instance to storageReference.
        storageReference = FirebaseStorage.getInstance().getReference();

        // Assign FirebaseDatabase instance with root database name.
        databaseReference = FirebaseDatabase.getInstance().getReference(Database_Path);

        //Assign ID'S to button.
        ChooseButton = (Button)findViewById(R.id.addimagebutton);
        UploadButton = (Button)findViewById(R.id.addimagebuttonsav);
        // Assign ID's to EditText.
        productname = (EditText)findViewById(R.id.productname); // Assign ID's to EditText.
        productprice = (EditText)findViewById(R.id.productprice); // Assign ID's to EditText.
        productsize = (EditText)findViewById(R.id.productsize); // Assign ID's to EditText.
        productdescription = (EditText)findViewById(R.id.productdescription); // Assign ID's to EditText.
        productimage = findViewById(R.id.imagprod); // Assign ID's to EditText.
        // Assigning Id to ProgressDialog.
        progressDialog = new ProgressDialog(Addproduct.this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

       textViewtoo = (TextView)toolbar.findViewById(R.id.toolbarTextView);

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
                SaveAdminProduct();

            }


        });


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });


        // create the get Intent object
        Intent intent = getIntent();
        // receive the value by getStringExtra() method
        // and key must be same which is send by first activity
        String str = intent.getStringExtra("Email_key");
        // display the string into textView

        textViewtoo.setText(str);

    }
    //////////on create methode


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {

            FilePathUri = data.getData();

            try {

                // Getting selected image into Bitmap.
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), FilePathUri);

                // Setting up bitmap selected image into ImageView.
                productimage.setImageBitmap(bitmap);

                // After selecting image change choose button above text.
                ChooseButton.setText("Product Selected");

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

    private void SaveAdminProduct() {

        // Checking whether FilePathUri Is empty or not.
        if (FilePathUri != null) {

            // Setting progressDialog Title.
            progressDialog.setTitle("Uploading...");
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
                            String emailP = textViewtoo.getText().toString().trim(); ;
                            String NameP = productname.getText().toString().trim();
                            String priceP = productprice.getText().toString().trim();
                            String sizeP = productsize.getText().toString().trim();
                            String descriptionP = productdescription.getText().toString().trim();
                          // Hiding the progressDialog after done uploading.
                            progressDialog.dismiss();
                            // Showing toast message after done uploading.
                            Toast.makeText(getApplicationContext(), " Product Successfully ", Toast.LENGTH_LONG).show();
                            Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!urlTask.isSuccessful());
                            Uri downloadUrl = urlTask.getResult();
//continue with your code
                            @SuppressWarnings("VisibleForTests")
                            addproductclass Adminprodlobjc = new addproductclass(
                                    emailP,
                                    downloadUrl.toString(),
                                    NameP,
                                    priceP,
                                    sizeP,
                                    descriptionP);

                            // Getting image upload ID.
                            String ImageAdminId = databaseReference.push().getKey();
                            // Adding image upload id s child element into databaseReference.
                            databaseReference.child(ImageAdminId).setValue(Adminprodlobjc);
                        }


                    })
                    // If something goes wrong .
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {

                            // Hiding the progressDialog.
                            progressDialog.dismiss();

                            // Showing exception erro message.
                            Toast.makeText(Addproduct.this, exception.getMessage(), Toast.LENGTH_LONG).show();
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

            Toast.makeText(Addproduct.this, "Please Select Image or Add Image Name", Toast.LENGTH_LONG).show();

        }

    }
}