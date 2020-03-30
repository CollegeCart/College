package com.example.collegecart;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.usage.NetworkStats;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.transition.Fade;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import de.hdodenhof.circleimageview.CircleImageView;

public class Main3Activity extends AppCompatActivity {

    Button logout;
    FirebaseAuth auth;
    TextView myProducts;
    CircleImageView imgView;
    CircleImageView circleImageView;
    ImageView search;
    TextView savedProducts , contactInfoo;
    private TextView title;
    ImageView sharebutton;
    private TextView additem;
    FirebaseUser usr;
    private ImageView deleteProducts;
    private ImageView favorites;
    private CircleImageView profilepic;
    private FirebaseFirestore db;
    private FirebaseUser user;
    private String gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main3);


      getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
      getSupportActionBar().setDisplayShowCustomEnabled(true);
      getSupportActionBar().setCustomView(R.layout.toolbaaar);

      View view = getSupportActionBar().getCustomView();

      db = FirebaseFirestore.getInstance();
      user = FirebaseAuth.getInstance().getCurrentUser();


        deleteProducts = view.findViewById(R.id.deleteProducts);
        deleteProducts.setVisibility(View.GONE);
        title = view.findViewById(R.id.ti);
        title.setText("Profile");
        favorites = view.findViewById(R.id.SaveTofavorites);
        favorites.setVisibility(View.GONE);
        profilepic =findViewById(R.id.circleView);

        sharebutton = view.findViewById(R.id.shareButton);
        sharebutton.setVisibility(View.GONE);




        additem = view.findViewById(R.id.addItemToolbar);
        savedProducts = findViewById(R.id.textView2);
        contactInfoo = findViewById(R.id.textView3);
        savedProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main3Activity.this , savedProducts.class);
                startActivity(intent);
            }
        });
        search = findViewById(R.id.searchProducts);

        contactInfoo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main3Activity.this , contacts.class);
                startActivity(intent);
            }
        });











        if (getIntent().getStringExtra("Gender").equals("Female"))
        {
            Glide.with(this).load(R.mipmap.female).into(profilepic);
        }
        else
        {

            Glide.with(this).load(R.mipmap.male).into(profilepic);

        }












        search.setVisibility(View.GONE);
        additem.setVisibility(View.GONE);
         circleImageView = view.findViewById(R.id.imageToolbar);



         myProducts =findViewById(R.id.textView);
         myProducts.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(Main3Activity.this ,myProducts.class);
                 startActivity(intent);

             }
         });


        circleImageView.setImageDrawable(getResources().getDrawable(R.mipmap.backarrow));
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });




        auth = FirebaseAuth.getInstance();

        imgView = findViewById(R.id.circleView);

         usr = auth.getCurrentUser();



        logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                auth.signOut();
                Intent intent = new Intent(Main3Activity.this , MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }




}
