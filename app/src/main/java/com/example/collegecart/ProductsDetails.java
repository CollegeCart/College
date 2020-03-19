package com.example.collegecart;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProductsDetails extends AppCompatActivity {


    TextView yearDetails;
    TextView brachDetails;
    FirebaseFirestore firestore;
    TextView subjectDetails;
    ImageView imageDEtails;
    TextView sellerCOntact;
    TextView sellerName;
    FirebaseUser user;
    TextView pricedetails;
    ImageView userImage;
    ImageView favorites;
    TextView addItems;
    TextView title;
    String uurl;
    ImageView searchItems;

    Button button ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_details);


        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.toolbaaar);

        View view = getSupportActionBar().getCustomView();

        userImage = view.findViewById(R.id.imageToolbar);
        userImage.setVisibility(View.GONE);
        title = view.findViewById(R.id.ti);
        title.setText("Product Details");
        favorites = view.findViewById(R.id.SaveTofavorites);
        addItems = view.findViewById(R.id.addItemToolbar);
        addItems.setVisibility(View.GONE);
        searchItems = view.findViewById(R.id.searchProducts);
        user = FirebaseAuth.getInstance().getCurrentUser();
        searchItems.setVisibility(View.GONE);
        firestore = FirebaseFirestore.getInstance();


        favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ProductsModel product = new ProductsModel(brachDetails.getText().toString() , subjectDetails.getText().toString() , yearDetails.getText().toString() ,null , uurl , pricedetails.getText().toString() );
                firestore.collection("Users").document(user.getUid()).collection("Favorites").add(product);

            }
        });




        yearDetails=  findViewById(R.id.textView6);
        subjectDetails = findViewById(R.id.textView5);
        brachDetails = findViewById(R.id.textView7);
        sellerName = findViewById(R.id.textView8);
        sellerCOntact  =findViewById(R.id.sellerContact);
        imageDEtails = findViewById(R.id.imageView2);
        pricedetails = findViewById(R.id.pricedetails);
        button =findViewById(R.id.loginDetails);
        button.setVisibility(View.GONE);


        firestore.collection("Users").document(user.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                sellerCOntact.setText( "Seller Conatct : " + documentSnapshot.get("Contact").toString());
            }
        });


        if (user == null)
        {
            button.setVisibility(View.VISIBLE);
            sellerName.setVisibility(View.GONE);
            sellerCOntact.setVisibility(View.GONE);


        }

        Intent intent = getIntent();
        subjectDetails.setText(intent.getStringExtra("Subject"));
        yearDetails.setText(intent.getStringExtra("Year"));
        brachDetails.setText(intent.getStringExtra("Branch"));
        pricedetails.setText(intent.getStringExtra("Price"));
        uurl = intent.getStringExtra("IMG");
      //  imageDEtails.setImageBitmap((Bitmap) intent.getParcelableExtra("IMG"));
        Glide.with(this).load(intent.getStringExtra("IMG")).skipMemoryCache(true).into(imageDEtails);




        sellerName.setText("Seller Name : " + user.getDisplayName());





    }
}
