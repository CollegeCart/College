package com.example.collegecart;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.github.ybq.android.spinkit.SpinKitView;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class sell extends AppCompatActivity {

    Spinner spinnercategories;
    FirebaseFirestore db;
    TextView additem;

    Spinner branchSpinner , yearSpinner , SubjectSpinner , greSpinner;
    String category;
    FirebaseAuth auth;
    String Branch , Year , Subject;
    Button updateButton;
        SpinKitView spinKitView;
        ArrayList<String> gatelist;
    ArrayAdapter subjectAdapter , branchAdapter , yearAdapter;
    SpinKitView imagespin;
    List<String> branchList , subjectList;
    String downloadURL;
    String Price;

    String username;
    ImageView searchProducts;


    EditText pname;
    ImageView imageView;
    EditText price;


    String greList[] = {"AWA(Analytical Writing)" , "Quantitative Reasoning" , "Verbal Reasoning" , "Physics" , "Chemistry" , "Mathematics" , "Literature In English" , "Biology" , "Psychology"};
    String gmatList[] = {"AWA(Analytical Writing)" , "Quantitative" , "Verbal" , "Integrated Reasoning"};
    String[]  Categories = {"Select Categories" ,"GMAT" , "Btech" , "GRE" , "Gate"};
    String[]  YearList = {"Second Year" , "Third Year" , "Fourth Year" , "Accessories"};
    private int CHOSE_IMAGE = 101;
    private Uri uriimage;
    private ImageView profileimage;
    private ImageView favorites;
    private ImageView deleteProducts;
    String BuyYear;
    ImageView sharebutton;
    CircleImageView profilepic;

    String userid;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);






        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.toolbaaar);

        View view = getSupportActionBar().getCustomView();
        title = view.findViewById(R.id.ti);
        title.setText("Add Product");

        deleteProducts = view.findViewById(R.id.deleteProducts);
        deleteProducts.setVisibility(View.GONE);

        favorites = view.findViewById(R.id.SaveTofavorites);
        sharebutton = view.findViewById(R.id.shareButton);
        sharebutton.setVisibility(View.GONE);
        favorites.setVisibility(View.GONE);
        profilepic = view.findViewById(R.id.imageToolbar);

        profilepic.setImageDrawable(getResources().getDrawable(R.mipmap.backarrow));
        profilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        additem = findViewById(R.id.addItemToolbar);
        additem.setVisibility(View.GONE);
        searchProducts = findViewById(R.id.searchProducts);
        searchProducts.setVisibility(View.GONE);

        gatelist  = new ArrayList<>();

        imagespin = findViewById(R.id.profileimageSpin);
        imagespin.setVisibility(View.GONE);






        profileimage = findViewById(R.id.productImagee);
        profileimage.setVisibility(View.GONE);
        auth = FirebaseAuth.getInstance();







        updateButton = findViewById(R.id.buttonToUpdateFirebase);
        updateButton.setVisibility(View.GONE);

        greSpinner = findViewById(R.id.greSpinner);
        branchSpinner = findViewById(R.id.branchspin);
        SubjectSpinner = findViewById(R.id.subjectspin);
        spinnercategories  = findViewById(R.id.categories);
        branchList = new ArrayList<>();

        spinnercategories.setVisibility(View.GONE);
        subjectList = new ArrayList<>();
        pname = findViewById(R.id.productNmae);
        pname.setVisibility(View.GONE);
        yearSpinner = findViewById(R.id.yearspin);
        db = FirebaseFirestore.getInstance();
        price = findViewById(R.id.editText);
        price.setVisibility(View.GONE);
        spinKitView = findViewById(R.id.spin_kit);
        spinKitView.setVisibility(View.VISIBLE);




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

                profileimage.setVisibility(View.VISIBLE);
                updateButton.setVisibility(View.VISIBLE);
                price.setVisibility(View.VISIBLE);
                pname.setVisibility(View.VISIBLE);

                db.collection("Users").document(userid).get().addOnSuccessListener(
                        new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {

                                username = documentSnapshot.getString("Username");

                            }
                        }
                );
                spinKitView.setVisibility(View.GONE);
                spinnercategories.setVisibility(View.VISIBLE);

            }
        },2000);



//--------------------------------Gate List---------------------------------------------------



        updateButton.setAlpha(.5f);
        updateButton.setEnabled(false);
            updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                if (isInternetConnection())
                {
                    if (downloadURL !=null && !category.equals("Select Categories")  &&  !price.getText().toString().equals("") &&  !pname.getText().toString().equals(""))
                    {

                        greSpinner.setVisibility(View.GONE);
                        branchSpinner.setVisibility(View.GONE);
                        yearSpinner.setVisibility(View.GONE);
                        profileimage.setVisibility(View.GONE);
                        SubjectSpinner.setVisibility(View.GONE);
                        spinnercategories.setVisibility(View.GONE);

                        updateButton.setVisibility(View.GONE);
                        price.setVisibility(View.GONE);
                        pname.setVisibility(View.GONE);


                        spinKitView.setVisibility(View.VISIBLE);

                        Random random = new Random();
                        final SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy-ss-hh-mm");
                        final String timestamp =   random.nextInt(1000000000)+format.format(new Date());



                        switch (category)
                        {
                            case "Btech" : {

                                if(Branch.equals("First Year"))
                                {

                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {

                                            Price = " Rs " + price.getText().toString();


                                            final ProductsModel product;

                                            product = new ProductsModel(pname.getText().toString() , Branch, Subject, "", category, downloadURL, Price, userid, username , timestamp);




                                            db.collection("Users").document(userid).collection("Products").document(timestamp).set(product).addOnSuccessListener(
                                                    new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            spinKitView.setVisibility(View.GONE);
                                                            Toast.makeText(sell.this, "Product Uploaded", Toast.LENGTH_SHORT).show();
                                                            Intent intent = new Intent(sell.this , myProducts.class);
                                                            startActivity(intent);
                                                            finish();
                                                        }
                                                    }
                                            );

                                        }
                                    }, 1000);










                                }
                                else
                                {

                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {

                                            Price = " Rs " + price.getText().toString();


                                            final ProductsModel product;

                                            product = new ProductsModel(pname.getText().toString() , Branch, Subject, Year, category, downloadURL, Price, userid, username , timestamp);




                                            db.collection("Users").document(userid).collection("Products").document(timestamp).set(product).addOnSuccessListener(
                                                    new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            spinKitView.setVisibility(View.GONE);
                                                            Toast.makeText(sell.this, "Product Uploaded", Toast.LENGTH_SHORT).show();
                                                            Intent intent = new Intent(sell.this , myProducts.class);
                                                            startActivity(intent);
                                                            finish();
                                                        }
                                                    }
                                            );

                                        }
                                    }, 1000);







                                }






                            }
                            break;
                            case "Gate" :
                            {



                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {


                                        Price = " Rs " + price.getText().toString();

                                        final ProductsModel product = new ProductsModel(pname.getText().toString() , branchSpinner.getSelectedItem().toString(), "", "", "Gate", downloadURL, Price, userid, username , timestamp);


                                        db.collection("Users").document(userid).collection("Products").document(timestamp).set(product).addOnSuccessListener(
                                                new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        spinKitView.setVisibility(View.GONE);

                                                        Toast.makeText(sell.this, "Product Uploaded", Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(sell.this , myProducts.class);
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                }
                                        );

                                    }
                                }, 1000);



                            }
                            break;

                            case "GRE" :
                            {





                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {


                                        Price = " Rs " + price.getText().toString();

                                        final ProductsModel product = new ProductsModel(pname.getText().toString() , "", Subject, "", "GRE", downloadURL, Price, userid, username , timestamp);


                                        db.collection("Users").document(userid).collection("Products").document(timestamp).set(product).addOnSuccessListener(
                                                new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        spinKitView.setVisibility(View.GONE);

                                                        Toast.makeText(sell.this, "Product Uploaded", Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(sell.this , myProducts.class);
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                }
                                        );

                                    }
                                }, 1000);



                            }
                            break;
                            case "GMAT" :
                            {

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {


                                        Price = " Rs " + price.getText().toString();

                                        final ProductsModel product = new ProductsModel(pname.getText().toString() , "", Subject, "", "GMAT", downloadURL, Price, userid, username ,timestamp);

                                        db.collection("Users").document(userid).collection("Products").document(timestamp).set(product).addOnSuccessListener(
                                                new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {

                                                        spinKitView.setVisibility(View.GONE);

                                                        Toast.makeText(sell.this, "Product Uploaded", Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(sell.this , myProducts.class);
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                }
                                        );


                                    }
                                }, 1000);


                            }
                            break;
                            default:
                            {
                                Toast.makeText(sell.this, "Invalid Choice", Toast.LENGTH_SHORT).show();
                            }
                        }



                    }
                    else
                    {
                        Toast.makeText(sell.this, "All Details Are Required", Toast.LENGTH_SHORT).show();
                    }

                }
                else
                {
                    Toast.makeText(sell.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }




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


                        if (Branch.equals("First Year"))
                        {
                            yearSpinner.setVisibility(View.GONE);

                        }
                        else
                        {
                            yearSpinner.setVisibility(View.VISIBLE);

                        }
                        greSpinner.setVisibility(View.GONE);
                        branchSpinner.setVisibility(View.VISIBLE);
                        SubjectSpinner.setVisibility(View.VISIBLE);


                    }
                    break;
                    case "GMAT":
                    {

                        greSpinner.setVisibility(View.GONE);
                        branchSpinner.setVisibility(View.GONE);
                        yearSpinner.setVisibility(View.GONE);
                        SubjectSpinner.setVisibility(View.VISIBLE);
                        gotoAdapter();

                    }
                    break;

                    case "GRE" :
                    {

                        branchSpinner.setVisibility(View.GONE);
                        yearSpinner.setVisibility(View.GONE);
                        SubjectSpinner.setVisibility(View.VISIBLE);
                        gotoAdapter();

                    }
                    break;
                    case "Gate" :
                    {

                        greSpinner.setVisibility(View.GONE);
                        branchSpinner.setVisibility(View.VISIBLE);
                        yearSpinner.setVisibility(View.GONE);
                        SubjectSpinner.setVisibility(View.GONE);
                        gotoAdapter();


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


        switch (category)
        {

            case "Btech" :
            {


                if (Branch.equals("First Year"))
                {


                    yearSpinner.setVisibility(View.GONE);

                    db.collection("Btech").document("First Year").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                            if (task.isSuccessful())
                            {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists())
                                {
                                    subjectList= (ArrayList<String>)document.get("First Year");

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
                else
                {

                    yearSpinner.setVisibility(View.VISIBLE);

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

            }
            break;


            case "GMAT" :
            {

                subjectAdapter= new ArrayAdapter(sell.this ,  android.R.layout.simple_spinner_item ,gmatList);
                subjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                SubjectSpinner.setAdapter(subjectAdapter);


            }
            break;




            case "GRE":
            {
                subjectAdapter= new ArrayAdapter(sell.this ,  android.R.layout.simple_spinner_item ,greList);
                subjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                SubjectSpinner.setAdapter(subjectAdapter);
            }
            break;


            case "Gate" :
            {


                subjectAdapter= new ArrayAdapter(sell.this ,  android.R.layout.simple_spinner_item ,subjectList);
                subjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                SubjectSpinner.setAdapter(subjectAdapter);

            }










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
                imagespin.setVisibility(View.VISIBLE);
profileimage.setAlpha(0.7f);
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
                            profileimage.setAlpha(1.0f);
                            imagespin.setVisibility(View.GONE);




                        }
                    });

                }
            });
        }



    }


    public void productImage(View view) {


        updateButton.setEnabled(true);
        updateButton.setAlpha(1f);
        Intent intent= new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent , "Select Image") , CHOSE_IMAGE);



    }




    public  boolean isInternetConnection()
    {
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(this.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            return  true;
        }
        else {
            return false;
        }
    }












}
