package com.example.collegecart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.IpSecManager;
import android.os.Bundle;
import android.os.Handler;
import android.transition.CircularPropagation;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProductsDetails extends AppCompatActivity {


    TextView yearDetails;
    TextView brachDetails;
    FirebaseFirestore firestore;
    SpinKitView spinKitView;
    TextView subjectDetails;
    ImageView imageDEtails;
    TextView sellerCOntact;
    String from;
    TextView sellerName;
    FirebaseUser user;
    TextView pricedetails;
    String othername;
    CircleImageView userImage;
    ImageView sharebutton;
    ImageView favorites;
    TextView addItems;
    TextView title;
    String uurl;
    TextView notlogintetxt;
    String ID;
    TextView productName;
    ImageView searchItems;

    Intent intent;
    Button button ;
    private ImageView deleteProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_details);


        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.toolbaaar);

        View view = getSupportActionBar().getCustomView();

        userImage = view.findViewById(R.id.imageToolbar);

        userImage.setImageDrawable(getResources().getDrawable(R.mipmap.backarrow));
        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });




        spinKitView = findViewById(R.id.detailsSpin);
        spinKitView.setVisibility(View.VISIBLE);
        title = view.findViewById(R.id.ti);
        notlogintetxt = findViewById(R.id.notloginText);
        deleteProducts = view.findViewById(R.id.deleteProducts);
        deleteProducts.setVisibility(View.GONE);

        sharebutton = view.findViewById(R.id.shareButton);
        sharebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT , "Hello PLz Have A Look At This App");
                intent.putExtra(Intent.EXTRA_TEXT , "https://play.google.com/store/apps/details?id=" +BuildConfig.APPLICATION_ID +"\n\n");
                startActivity(Intent.createChooser(intent , "Share Using"));
            }
        });
        title.setText("Product Details");
        favorites = view.findViewById(R.id.SaveTofavorites);
        productName =findViewById(R.id.textView11);

        yearDetails=  findViewById(R.id.textView6);
        subjectDetails = findViewById(R.id.textView5);
        brachDetails = findViewById(R.id.textView7);
        sellerName = findViewById(R.id.textView8);
        sellerCOntact  =findViewById(R.id.sellerContact);
        imageDEtails = findViewById(R.id.imageView2);
        pricedetails = findViewById(R.id.pricedetails);
        notlogintetxt.setVisibility(View.GONE);
        button =findViewById(R.id.loginDetails);
        button.setVisibility(View.GONE);

        addItems = view.findViewById(R.id.addItemToolbar);
        addItems.setVisibility(View.GONE);
        searchItems = view.findViewById(R.id.searchProducts);
        user = FirebaseAuth.getInstance().getCurrentUser();
        searchItems.setVisibility(View.GONE);

        intent  = getIntent();

        subjectDetails.setText(intent.getStringExtra("Subject"));
        yearDetails.setText(intent.getStringExtra("Year"));
        brachDetails.setText(intent.getStringExtra("Branch"));
        pricedetails.setText(intent.getStringExtra("Price"));
        productName.setText(intent.getStringExtra("productname"));


        subjectDetails.setVisibility(View.GONE);
        yearDetails.setVisibility(View.GONE);
        brachDetails.setVisibility(View.GONE);
        pricedetails.setVisibility(View.GONE);
        productName.setVisibility(View.GONE);
        sellerName.setVisibility(View.GONE);
        sellerCOntact.setVisibility(View.GONE);
        imageDEtails.setVisibility(View.GONE);





        if (user == null)
        {
            sharebutton.setVisibility(View.GONE);
            favorites.setVisibility(View.GONE);

        }





        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                spinKitView.setVisibility(View.GONE);


                subjectDetails.setVisibility(View.VISIBLE);
                yearDetails.setVisibility(View.VISIBLE);
                brachDetails.setVisibility(View.VISIBLE);
                pricedetails.setVisibility(View.VISIBLE);
                productName.setVisibility(View.VISIBLE);

                imageDEtails.setVisibility(View.VISIBLE);
                if (user != null)
                {
                    sellerName.setVisibility(View.VISIBLE);
                    sellerCOntact.setVisibility(View.VISIBLE);

                }
                else {

                    button.setVisibility(View.VISIBLE);
                    notlogintetxt.setVisibility(View.VISIBLE);



                }







            }
        } , 1000);






        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (intent.getStringExtra("from").equals("myproducts") || intent.getStringExtra("from").equals("saved")  || intent.getStringExtra("from").equals("search") )
                {

                    favorites.setVisibility(View.GONE);

                }

            }
        },200);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (intent.getStringExtra("from").equals("myproducts") || intent.getStringExtra("from").equals("saved") )
                {

                    favorites.setVisibility(View.GONE);
                    deleteProducts.setVisibility(View.VISIBLE);

                }

            }
        },100);



        if (intent.getStringExtra("from").equals("firestore") )
        {

            if(user != null) {
                if (intent.getStringExtra("ID").equals(user.getUid())) {
                    favorites.setVisibility(View.GONE);
                }
            }
        }








        deleteProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ((intent.getStringExtra("from").equals("myproducts")))
                {

                    AlertDialog.Builder builder = new AlertDialog.Builder(ProductsDetails.this);
                    builder.setMessage("Sure! You Want To Delete").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            firestore.collection("Users").document(user.getUid()).collection("Products")
                                    .document(intent.getStringExtra("time")).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(ProductsDetails.this, "Product Deleted", Toast.LENGTH_SHORT).show();

                                }
                            });


                            Intent intent1 = new Intent(ProductsDetails.this , myProducts.class);
                            startActivity(intent1);
                            finish();
                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).setCancelable(false).show();

                }

                if ((intent.getStringExtra("from").equals("saved")))
                {

                    AlertDialog.Builder builder = new AlertDialog.Builder(ProductsDetails.this);
                    builder.setMessage("Sure! You Want To Delete The Products From Saved ").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            firestore.collection("Users").document(user.getUid()).collection("Favorites")
                                    .document(intent.getStringExtra("time")).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(ProductsDetails.this, "Product Deleted", Toast.LENGTH_SHORT).show();

                                }
                            });


                            Intent intent1 = new Intent(ProductsDetails.this , savedProducts.class);
                            startActivity(intent1);
                            finish();
                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).setCancelable(false).show();


                }







            }
        });





        switch(intent.getStringExtra("category"))
        {


            case "Btech":
            {


            }
            break;

            case "Gate":
            {

                yearDetails.setVisibility(View.GONE);
                subjectDetails.setVisibility(View.GONE);



            }
            break;
            case "GRE":
            {

                brachDetails.setVisibility(View.GONE);
                yearDetails.setVisibility(View.GONE);


            }
            break;
            case "GMAT":
            {
                yearDetails.setVisibility(View.GONE); brachDetails.setVisibility(View.GONE);
                brachDetails.setVisibility(View.GONE);

            }
            break;
        }

        ID = intent.getStringExtra("ID");
        uurl = intent.getStringExtra("IMG");
        othername = intent.getStringExtra("username");
        Glide.with(this).load(intent.getStringExtra("IMG")).skipMemoryCache(true).into(imageDEtails);




        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductsDetails.this , MainActivity.class);
                startActivity(intent);
                finish();
            }
        });




        firestore = FirebaseFirestore.getInstance();


        favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                switch(intent.getStringExtra("category"))
                {


                    case "Btech":
                    {

                        SimpleDateFormat format = new SimpleDateFormat("dd-mm-yyyymm-ss-hh");
                        String random = format.format(new Date());
                        ProductsModel product = new ProductsModel(productName.getText().toString(),brachDetails.getText().toString() , subjectDetails.getText().toString() , yearDetails.getText().toString() ,"" , uurl , pricedetails.getText().toString() , ID , othername , random);
                        firestore.collection("Users").document(user.getUid()).collection("Favorites").document(random).set(product);

                        Toast.makeText(ProductsDetails.this, "Product Saved", Toast.LENGTH_SHORT).show();









                    }
                    break;

                    case "Gate":
                    {


                        SimpleDateFormat format = new SimpleDateFormat("dd-mm-yyyymm-ss-hh");
                        String random = format.format(new Date());
                        ProductsModel product = new ProductsModel(productName.getText().toString(),brachDetails.getText().toString() , "" , "","" , uurl , pricedetails.getText().toString() , ID , othername , random);
                        firestore.collection("Users").document(user.getUid()).collection("Favorites").document(random).set(product);

                        Toast.makeText(ProductsDetails.this, "Product Saved", Toast.LENGTH_SHORT).show();




                    }
                    break;
                    case "GRE":
                    {


                        SimpleDateFormat format = new SimpleDateFormat("dd-mm-yyyymm-ss-hh");
                        String random = format.format(new Date());
                        ProductsModel product = new ProductsModel(productName.getText().toString(),"", subjectDetails.getText().toString() , "" ,intent.getStringExtra("category") , uurl , pricedetails.getText().toString() , ID , othername , random);
                        firestore.collection("Users").document(user.getUid()).collection("Favorites").document(random).set(product);

                        Toast.makeText(ProductsDetails.this, "Product Saved", Toast.LENGTH_SHORT).show();








                    }
                    break;
                    case "GMAT":
                    {

                        SimpleDateFormat format = new SimpleDateFormat("dd-mm-yyyymm-ss-hh");
                        String random = format.format(new Date());
                        ProductsModel product = new ProductsModel(productName.getText().toString(),"" , subjectDetails.getText().toString() , "" ,"GMAT", uurl , pricedetails.getText().toString() , ID , othername , random);
                        firestore.collection("Users").document(user.getUid()).collection("Favorites").document(random).set(product);

                        Toast.makeText(ProductsDetails.this, "Product Saved", Toast.LENGTH_SHORT).show();







                    }
                    break;
                }
















            }
        });





        imageDEtails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductsDetails.this ,  fullimage.class);
                intent.putExtra("Image" , uurl);
                startActivity(intent);

            }
        });







       new Handler().postDelayed(new Runnable() {
           @Override
           public void run() {


               firestore.collection("Users").document(ID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                   @Override
                   public void onSuccess(DocumentSnapshot documentSnapshot) {

                       sellerCOntact.setText( "Contact : " + documentSnapshot.get("Contact").toString());
                       sellerName.setText("Seller : " + documentSnapshot.get("Username").toString());
                   }
               });


           }
       },400);







    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();


        if (intent.getStringExtra("from").equals("myproducts") )
        {
            Intent intent = new Intent(this , myProducts.class);
            startActivity(intent);
            finish();

        }



        if (intent.getStringExtra("from").equals("saved") )
        {
            Intent intent = new Intent(this , savedProducts.class);
            startActivity(intent);
            finish();

        }





    }
}
