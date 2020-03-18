package com.example.collegecart;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
    private TextView additem;
    FirebaseUser usr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main3);

      getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
      getSupportActionBar().setDisplayShowCustomEnabled(true);
      getSupportActionBar().setCustomView(R.layout.toolbaaar);

      View view = getSupportActionBar().getCustomView();



        title = view.findViewById(R.id.ti);
        title.setText("Profile");
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

        circleImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));






        auth = FirebaseAuth.getInstance();

        imgView = findViewById(R.id.circleView);

         usr = auth.getCurrentUser();
     new Handler().postDelayed(new Runnable() {
         @Override
         public void run() {


             if (usr != null)
             {
                 String picIage = usr.getPhotoUrl().toString();

                 Toast.makeText(Main3Activity.this, picIage, Toast.LENGTH_SHORT).show();
                 Glide.with(Main3Activity.this).load(picIage).skipMemoryCache(true).into(imgView);
             }


         }
     },2000);



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
