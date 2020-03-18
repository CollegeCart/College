package com.example.collegecart;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.SuccessContinuation;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class profile extends AppCompatActivity {

    private static final int CHOSE_IMAGE = 101;
    private static final int KITKAT_VALUE =10 ;

    Uri uriimage;
CircleImageView profileimage;
FirebaseFirestore db;
String userid;
    String url;
EditText username , number , email;
LinearLayout layout;

ProgressBar progressBar;
String downloadURL;
FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        username = findViewById(R.id.username);


        progressBar = findViewById(R.id.progressBar);
        number = findViewById(R.id.number);
        email = findViewById(R.id.email);


        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        layout =  findViewById(R.id.profileLinearLaout);
        layout.setVisibility(View.GONE);


        userid = auth.getUid();

        db.collection("Users").document(userid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                url = documentSnapshot.getString("profileurl");



            }
        });


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (url !=null)
                {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(profile.this, url + "hanjiiiiiiiiii", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(profile.this , firestoreRycycler.class);
                    startActivity(intent);
                }
                else
                {
                    progressBar.setVisibility(View.GONE);
                    layout.setVisibility(View.VISIBLE);
                }

            }
        },2000);


        profileimage = findViewById(R.id.imageView);






    }

    public void onFinsh(View view) {



        saveTofireBase();
        Intent intent = new Intent(this , firestoreRycycler.class);
        startActivity(intent);
        finish();


    }

    private void saveTofireBase() {
        String displauname = username.getText().toString();

        if (displauname.isEmpty())
        {
            Toast.makeText(this, "UserName Is Required", Toast.LENGTH_SHORT).show();
        }

        FirebaseUser user = auth.getCurrentUser();
        if (user !=null && uriimage !=null)
        {
            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                    .setDisplayName(displauname)
                    .setPhotoUri(uriimage)
                    .build();

            downloadURL = uriimage.toString();
            Map<String , String> url = new HashMap<>();
            url.put("profileurl" , downloadURL);
            url.put("Email" , email.getText().toString());
            url.put("Number" , number.getText().toString());
            db.collection("Users").document(userid).set(url);



            user.updateProfile(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    Toast.makeText(profile.this, "Profile Updated", Toast.LENGTH_SHORT).show();

                }
            });
        }



    }

    private void uploadtofirebase() {

        final StorageReference profileImageref = FirebaseStorage.getInstance().getReference("profilepics/" + System.currentTimeMillis() + ".jpg");

        if (uriimage !=null)
        {
            profileImageref.putFile(uriimage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                profileImageref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {


                        Uri downLoaduri = uri;

                        Map<String , String> url = new HashMap<>();
                        url.put("profileurl" , downloadURL);
                        url.put("Email" , email.getText().toString());
                        url.put("Number" , number.getText().toString());
                        db.collection("Users").document(userid).set(url);






                    }
                });


                }
            });
        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == CHOSE_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
          uriimage =  data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver() , uriimage);

                profileimage.setImageBitmap(bitmap);

            saveTofireBase();

            } catch (IOException e) {
                e.printStackTrace();
            }


        }



    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();


        Toast.makeText(this, "Press Again", Toast.LENGTH_SHORT).show();
    }

    public void proFilepicTure(View view) {


        Intent intent;

        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            startActivityForResult(intent, CHOSE_IMAGE);
        } else {
            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("*/*");
            startActivityForResult(intent, CHOSE_IMAGE);
        }

















     /*

        Intent intent= new Intent();
        intent.setType("image/*");

        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        if (intent.resolveActivity(getPackageManager()) != null)
        startActivityForResult(Intent.createChooser(intent , "Select Image") , CHOSE_IMAGE);
*/


    }



}