package com.example.collegecart;

import androidx.annotation.Keep;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.ybq.android.spinkit.SpinKitView;
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


CircleImageView profileimage;
@Keep
FirebaseFirestore db;
@Keep
String userid;
    String urrl;
    SpinKitView spinKitView;
    RadioGroup genderGroup;
    @Keep
EditText username , number , email;
LinearLayout layout;
    Map<String , String> url;
RadioButton female,male;

@Keep
FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        username = findViewById(R.id.username);


        number = findViewById(R.id.number);
        email = findViewById(R.id.email);
        male = findViewById(R.id.maleradio);
        female = findViewById(R.id.femaleRadio);



        auth = FirebaseAuth.getInstance();

        db = FirebaseFirestore.getInstance();
        spinKitView = findViewById(R.id.spinprofile);
        layout =  findViewById(R.id.profileLinearLaout);
        layout.setVisibility(View.GONE);

        spinKitView.setVisibility(View.GONE);

        userid = auth.getUid();


        db.collection("Users").document(userid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                urrl = documentSnapshot.getString("Contact");
                Toast.makeText(profile.this, urrl, Toast.LENGTH_SHORT).show();



            }
        });






        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (urrl != null)
                {

                    Intent intent = new Intent(profile.this , firestoreRycycler.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    layout.setVisibility(View.VISIBLE);
                }

            }
        },2000);




    }

    public void onFinsh(View view) {





        if (email.getText().toString().isEmpty() || number.getText().toString().isEmpty() || username.getText().toString().isEmpty() || !(male.isChecked() || female.isChecked()))
        {
            Toast.makeText(this, "All Field Is Required", Toast.LENGTH_SHORT).show();

        }
        else {

            spinKitView.setVisibility(View.VISIBLE);
            layout.setVisibility(View.GONE);
            saveTofireBase();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {


                    spinKitView.setVisibility(View.GONE);

                    Intent intent = new Intent(profile.this , firestoreRycycler.class);
                    startActivity(intent);
                    finish();



                }
            } , 1500);
        }




    }

    private void saveTofireBase() {




          url = new HashMap<>();

            if (female.isChecked())
            {
                url.put("Gender" , "Female");

            }
            if (male.isChecked())
            {
                url.put("Gender" ,"Male");


            }

        Toast.makeText(this, url.get("Gender"), Toast.LENGTH_SHORT).show();
            url.put("Username" , username.getText().toString());
            url.put("Email" , email.getText().toString());
            url.put("Contact" , email.getText().toString());
            url.put("Number" , number.getText().toString());
            db.collection("Users").document(userid).set(url);











    }



    }



