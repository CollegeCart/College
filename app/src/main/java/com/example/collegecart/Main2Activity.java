package com.example.collegecart;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Main2Activity extends AppCompatActivity {



    Spinner buyspinnercategories;
    FirebaseFirestore database;
    FirebaseAuth auth;
    private TextView title;
    ImageView sharebutton;
    ImageView searchProducts;
    CircleImageView profilepic;

    TextView additem;
    ArrayAdapter subjectAdapter , branchAdapter , yearAdapter;
    Spinner BuybranchSpinner , BuyyearSpinner , BuySubjectSpinner , BuygreSpinner;
    String greList[] = {"AWA(Analytical Writing)" , "Quantitative Reasoning" , "Verbal Reasoning" , "Physics" , "Chemistry" , "Mathematics" , "Literature In English" , "Biology" , "Psychology"};
    String gmatList[] = {"AWA(Analytical Writing)" , "Quantitative" , "Verbal" , "Integrated Reasoning"};
    List<String> BuybranchList , BuysubjectList;

    String[]  BuyCategories = {"Select Categories" ,"GMAT" , "Btech" , "GRE" , "Gate" };
    String[]  BuyYearList = {"Second Year" , "Third Year" , "Fourth Year", "Accesories"};
    CircleImageView imageView;
     String category;
     String BuyBranch;
    private ImageView favorites;
    private ImageView deleteProducts;
     String Subject;
     String BuyYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);



        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();



        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.toolbaaar);

        View view = getSupportActionBar().getCustomView();
        title = view.findViewById(R.id.ti);
        title.setText("Search Product");

        deleteProducts = view.findViewById(R.id.deleteProducts);
        deleteProducts.setVisibility(View.GONE);

        favorites = view.findViewById(R.id.SaveTofavorites);
        favorites.setVisibility(View.GONE);
        profilepic = view.findViewById(R.id.imageToolbar);
        profilepic.setVisibility(View.GONE);

        additem = findViewById(R.id.addItemToolbar);
        additem.setVisibility(View.GONE);
        searchProducts = findViewById(R.id.searchProducts);
        searchProducts.setVisibility(View.GONE);
        sharebutton = view.findViewById(R.id.shareButton);
        sharebutton.setVisibility(View.GONE);





        BuybranchSpinner = findViewById(R.id.BUYbranchspin);
        BuygreSpinner = findViewById(R.id.BuygreSpinner);
        BuySubjectSpinner = findViewById(R.id.Buysubjectspin);
        database = FirebaseFirestore.getInstance();
        buyspinnercategories  = findViewById(R.id.Buycategories);
        BuyyearSpinner = findViewById(R.id.Buyyearspin);;


        BuybranchList = new ArrayList<>();
        BuysubjectList = new ArrayList<>();





        BuybranchSpinner.setVisibility(View.GONE);
        BuyyearSpinner.setVisibility(View.GONE);
        BuySubjectSpinner.setVisibility(View.GONE);


        database.collection("Btech").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful())
                {
                    for (QueryDocumentSnapshot snapshot : task.getResult())
                    {
                        BuybranchList.add(snapshot.getId());
                    }
                }




                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        branchAdapter= new ArrayAdapter(Main2Activity.this ,  android.R.layout.simple_spinner_item ,BuybranchList);
                        branchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        BuybranchSpinner.setAdapter(branchAdapter);

                    

                    }
                },1000);

            }
        });






        //*******************************YEAR SPINNER*************************************************


        yearAdapter= new ArrayAdapter(Main2Activity.this ,  android.R.layout.simple_spinner_item ,BuyYearList );
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        BuyyearSpinner.setAdapter(yearAdapter);



        //*********************************************************************************************

//**********************************************BranchSpinner***********************************************


        BuyYear = "Second Year";
        BuyBranch = "CS";

        BuybranchSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                BuyBranch = parent.getItemAtPosition(position).toString();

                gotoAdapter();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });







        //****************************YEAR SPINNER********************************************************

        BuyyearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                BuyYear = parent.getItemAtPosition(position).toString();
                gotoAdapter();

            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                buyspinnercategories.setVisibility(View.VISIBLE);

            }
        },2000);



//------------------------------------CATEGORIES SELECTION--------------------------------------------------------




        ArrayAdapter adapter = new ArrayAdapter(this ,  android.R.layout.simple_spinner_item ,BuyCategories );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        buyspinnercategories.setAdapter(adapter);

        buyspinnercategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                category = parent.getItemAtPosition(position).toString();

                switch (category)
                {
                    case "Btech":
                    {
                        if (BuyBranch.equals("First Year"))
                        {

                            BuyyearSpinner.setVisibility(View.GONE);


                        }
                        else
                        {
                            BuyyearSpinner.setVisibility(View.VISIBLE);

                        }
                        BuygreSpinner.setVisibility(View.GONE);
                        BuybranchSpinner.setVisibility(View.VISIBLE);
                        BuySubjectSpinner.setVisibility(View.VISIBLE);


                    }
                    break;
                    case "GMAT":
                    {



                        BuygreSpinner.setVisibility(View.GONE);
                        BuybranchSpinner.setVisibility(View.GONE);
                        BuyyearSpinner.setVisibility(View.GONE);
                        BuySubjectSpinner.setVisibility(View.GONE);
                        gotoAdapter();



                    }
                    break;

                    case "GRE" :
                    {

                        BuygreSpinner.setVisibility(View.GONE);
                        BuybranchSpinner.setVisibility(View.GONE);
                        BuyyearSpinner.setVisibility(View.GONE);
                        BuySubjectSpinner.setVisibility(View.GONE);
                        gotoAdapter();


                    }
                    break;
                    case "Gate" :
                    {

                        BuygreSpinner.setVisibility(View.GONE);
                        BuybranchSpinner.setVisibility(View.VISIBLE);
                        BuyyearSpinner.setVisibility(View.GONE);
                        BuySubjectSpinner.setVisibility(View.GONE);



                    }
                    break;

                }


                category = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });







        BuySubjectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                Subject = parent.getItemAtPosition(position).toString();

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


                if (BuyBranch.equals("First Year"))
                {



                    BuyyearSpinner.setVisibility(View.GONE);
                    database.collection("Btech").document("First Year").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                            if (task.isSuccessful())
                            {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists())
                                {
                                    BuysubjectList= (ArrayList<String>)document.get("First Year");

                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {

                                            subjectAdapter= new ArrayAdapter(Main2Activity.this ,  android.R.layout.simple_spinner_item ,BuysubjectList);
                                            subjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                            BuySubjectSpinner.setAdapter(subjectAdapter);



                                        }
                                    },300);


                                }
                            }





                        }
                    });







                }
                else
                {


                    BuyyearSpinner.setVisibility(View.VISIBLE);
                    database.collection("Btech").document(BuyBranch).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                            if (task.isSuccessful())
                            {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists())
                                {
                                    BuysubjectList= (ArrayList<String>)document.get(BuyYear);

                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {

                                            subjectAdapter= new ArrayAdapter(Main2Activity.this ,  android.R.layout.simple_spinner_item ,BuysubjectList);
                                            subjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                            BuySubjectSpinner.setAdapter(subjectAdapter);



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

                subjectAdapter= new ArrayAdapter(Main2Activity.this ,  android.R.layout.simple_spinner_item ,gmatList);
                subjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                BuySubjectSpinner.setAdapter(subjectAdapter);


            }
            break;




            case "GRE":
            {
                subjectAdapter= new ArrayAdapter(Main2Activity.this ,  android.R.layout.simple_spinner_item ,greList);
                subjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                BuySubjectSpinner.setAdapter(subjectAdapter);
            }
            break;


            case "Gate" :
            {


                subjectAdapter= new ArrayAdapter(Main2Activity.this ,  android.R.layout.simple_spinner_item , BuysubjectList);
                subjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                BuySubjectSpinner.setAdapter(subjectAdapter);





            }










        }





    }


    public void productlist(View view) {

        Intent intent = new Intent(this , searchResults.class);
        intent.putExtra("category" , category);
        intent.putExtra("Subject" , Subject);
        intent.putExtra("branch" , BuyBranch);
        startActivity(intent);
        finish();


    }
}
