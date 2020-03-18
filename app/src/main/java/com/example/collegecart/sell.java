package com.example.collegecart;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class sell extends AppCompatActivity {

    Spinner spinnercategories;
    ProgressDialog progressDialog;
    FirebaseFirestore db;
    Spinner branchSpinner , yearSpinner , SubjectSpinner , greSpinner;
    String category;
    FirebaseAuth auth;
    String Branch , Year , Subject;
    Button updateButton;
    ArrayAdapter subjectAdapter , branchAdapter , yearAdapter;
    List<String> branchList , subjectList;
    String downloadURL;
    String Price;
    ImageView imageView;
    EditText price;


    String[]  Categories = {"Select Categories" ,"GMAT" , "Btech" , "GRE" , "Gate"};
    String[]  YearList = {"Second Year" , "Third Year" , "Fourth Year"};
    private int CHOSE_IMAGE = 101;
    private Uri uriimage;
    private ImageView profileimage;
    String userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);







        profileimage = findViewById(R.id.productImagee);
        auth = FirebaseAuth.getInstance();






        progressDialog = new ProgressDialog(this);

        progressDialog.setTitle("Contents Loading");
        updateButton = findViewById(R.id.buttonToUpdateFirebase);
        progressDialog.setMessage("Loading.......");
        progressDialog.show();

        greSpinner = findViewById(R.id.greSpinner);
        branchSpinner = findViewById(R.id.branchspin);
        SubjectSpinner = findViewById(R.id.subjectspin);
        spinnercategories  = findViewById(R.id.categories);
        branchList = new ArrayList<>();
        spinnercategories.setVisibility(View.GONE);
        subjectList = new ArrayList<>();
        yearSpinner = findViewById(R.id.yearspin);
        db = FirebaseFirestore.getInstance();
        price = findViewById(R.id.editText);


        branchSpinner.setVisibility(View.GONE);
        yearSpinner.setVisibility(View.GONE);
        SubjectSpinner.setVisibility(View.GONE);
        Year = "Second Year";
        Branch = "CS";

        greSpinner.setVisibility(View.GONE);
         userid = auth.getUid();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


             progressDialog.dismiss();
                spinnercategories.setVisibility(View.VISIBLE);

            }
        },2000);


        updateButton.setAlpha(.5f);
        updateButton.setClickable(false);
            updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                progressDialog.show();




                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {


                        progressDialog.dismiss();

                        Price = " Rs " + price.getText().toString();

                        final ProductsModel product = new ProductsModel(Branch , Subject , Year , category , downloadURL , Price );





                        db.collection("Users").document(userid).collection("Products").add(product);
                        Toast.makeText(sell.this, product.getImgUrl(), Toast.LENGTH_SHORT).show();

                    }
                } , 1000);

            }
        });




        //*******************************YEAR SPINNER*************************************************


        yearAdapter= new ArrayAdapter(sell.this ,  android.R.layout.simple_spinner_item ,YearList );
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(yearAdapter);



        //*********************************************************************************************








        //---------------------------------Branch Database--------------------------------------------





        db.collection("Btech").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful())
                {
                    for (QueryDocumentSnapshot snapshot : task.getResult())
                    {
                        branchList.add(snapshot.getId());
                    }
                }



                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        branchAdapter= new ArrayAdapter(sell.this ,  android.R.layout.simple_spinner_item ,branchList );
                        branchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        branchSpinner.setAdapter(branchAdapter);


                    }
                },700);

            }
        });















        //*************************************Year Selection*****************************************************

//**********************************************BranchSpinner***********************************************

        branchSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Branch = parent.getItemAtPosition(position).toString();

                gotoAdapter();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Year = parent.getItemAtPosition(position).toString();
                gotoAdapter();

            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


//**************************************** SUBJECT SPINNER************************************************
        SubjectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Subject = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





//------------------------------------CATEGORIES SELECTION--------------------------------------------------------




        ArrayAdapter adapter = new ArrayAdapter(this ,  android.R.layout.simple_spinner_item ,Categories );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnercategories.setAdapter(adapter);

        spinnercategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                category = parent.getItemAtPosition(position).toString();

                switch (category)
                {
                    case "Btech":
                    {

                        greSpinner.setVisibility(View.GONE);
                        branchSpinner.setVisibility(View.VISIBLE);
                        yearSpinner.setVisibility(View.VISIBLE);
                        SubjectSpinner.setVisibility(View.VISIBLE);


                    }
                    break;
                    case "GMAT":
                    {

                        greSpinner.setVisibility(View.GONE);
                        branchSpinner.setVisibility(View.GONE);
                        yearSpinner.setVisibility(View.GONE);
                        SubjectSpinner.setVisibility(View.GONE);

                    }
                    break;

                    case "GRE" :
                    {

                        greSpinner.setVisibility(View.VISIBLE);
                        branchSpinner.setVisibility(View.GONE);
                        yearSpinner.setVisibility(View.GONE);
                        SubjectSpinner.setVisibility(View.GONE);

                    }
                    break;
                    case "Gate" :
                    {

                        greSpinner.setVisibility(View.GONE);
                        branchSpinner.setVisibility(View.VISIBLE);
                        yearSpinner.setVisibility(View.GONE);
                        SubjectSpinner.setVisibility(View.GONE);


                    }
                    break;

                }


                category = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });









    }

    private void gotoAdapter() {




        db.collection("Btech").document(Branch).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful())
                {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists())
                    {
                        subjectList= (ArrayList<String>)document.get(Year);

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                subjectAdapter= new ArrayAdapter(sell.this ,  android.R.layout.simple_spinner_item ,subjectList);
                                subjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                SubjectSpinner.setAdapter(subjectAdapter);



                            }
                        },300);


                    }
                }





            }
        });












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

                uploadTofirestore();


            } catch (IOException e) {
                e.printStackTrace();
            }


        }



    }

    private void uploadTofirestore() {


        final StorageReference productImageref = FirebaseStorage.getInstance().getReference("productspics/" + System.currentTimeMillis() + ".jpg");

        if (uriimage !=null)
        {
            productImageref.putFile(uriimage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    productImageref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            Uri downloadURI = uri;
                            downloadURL = downloadURI.toString();
                            updateButton.setClickable(true);
                            updateButton.setAlpha(1f);



                        }
                    });

                }
            });
        }



    }


    public void productImage(View view) {


        Intent intent= new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent , "Select Image") , CHOSE_IMAGE);



    }
}
