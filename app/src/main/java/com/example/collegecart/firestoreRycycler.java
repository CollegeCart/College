package com.example.collegecart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import de.hdodenhof.circleimageview.CircleImageView;

public class firestoreRycycler extends AppCompatActivity {

 private   FirebaseFirestore firebaseFirestore;
    private RecyclerView recyclerView;

    FirestoreRecyclerAdapter adapter;
    FirebaseFirestore db;
    ImageView favorites;
    CircleImageView imageToolbar;
    private FirebaseUser user;
    String profileurl;
    private TextView title;
    ImageView searchProducts;
    TextView additem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firestore_rycycler);





        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.toolbaaar);

        View view = getSupportActionBar().getCustomView();
        title = view.findViewById(R.id.ti);
        title.setText("Products");
        additem = view.findViewById(R.id.addItemToolbar);
        additem.setVisibility(View.GONE);

        favorites = view.findViewById(R.id.SaveTofavorites);
        favorites.setVisibility(View.GONE);



        imageToolbar = view.findViewById(R.id.imageToolbar);
        db = FirebaseFirestore.getInstance();
        searchProducts = findViewById(R.id.searchProducts);
        searchProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(firestoreRycycler.this , Main2Activity.class);
                startActivity(intent);

            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();


        if (user != null)
        {

            /*
            db.collection("Users").document(user.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {


                    profileurl = documentSnapshot.getString("profileurl");

                }
            });
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(firestoreRycycler.this, profileurl, Toast.LENGTH_SHORT).show();
                    Glide.with(firestoreRycycler.this).load(profileurl).into(imageToolbar);
                }
            },1000);



             */


            String picIage = user.getPhotoUrl().toString();
            Toast.makeText(this, picIage, Toast.LENGTH_SHORT).show();
            Glide.with(this).load(picIage).skipMemoryCache(true).into(imageToolbar);



        }





        imageToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user !=null)
                {
                    Intent intent = new Intent(firestoreRycycler.this , Main3Activity.class);
                    startActivity(intent);
                }
                else {

                    Intent intent = new Intent(firestoreRycycler.this , MainActivity.class);
                    startActivity(intent);

                }
            }
        });

        recyclerView = findViewById(R.id.ryclerId);
        firebaseFirestore = FirebaseFirestore.getInstance();


        Query query = firebaseFirestore.collectionGroup("Products");


        FirestoreRecyclerOptions<ProductsModel> options = new FirestoreRecyclerOptions.Builder<ProductsModel>()
                .setQuery(query , ProductsModel.class).build();


        adapter = new FirestoreRecyclerAdapter<ProductsModel, ProductsViewHolder>(options) {
            @NonNull
            @Override
            public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {



                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.buyproducts ,  parent , false);
                return new ProductsViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull ProductsViewHolder holder, int position, @NonNull ProductsModel model) {



                holder.subject.setText(model.getSubject());
                holder.year.setText(model.getYear());
                holder.branch.setText(model.getBranch());
                holder.url = model.getImgUrl();
                holder.price.setText(model.getPrice());
                Glide.with(firestoreRycycler.this).load(model.getImgUrl()).into(holder.image);



            }
        };

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);






    }

    public class ProductsViewHolder extends  RecyclerView.ViewHolder{

        private TextView branch;

        private TextView year;

        private TextView subject;
        TextView price;
        private ImageView image;
        String url;
        LinearLayout linearLayout;



        public ProductsViewHolder(@NonNull View itemView) {
            super(itemView);

            branch = itemView.findViewById(R.id.BranchRecycler);
            year =itemView.findViewById(R.id.yearRecycler);
            subject = itemView.findViewById(R.id.subjectReycler);
            price = itemView.findViewById(R.id.pricerecycler);

            linearLayout = itemView.findViewById(R.id.recycleLayout);
            image= itemView.findViewById(R.id.RecyclerImage);
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                   // Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
                    Intent intent = new Intent(firestoreRycycler.this , ProductsDetails.class);
                    intent.putExtra("Subject" , subject.getText().toString());
                    intent.putExtra("Year" , year.getText().toString());
                    intent.putExtra("Branch" , branch.getText().toString());
                    intent.putExtra("Price" , price.getText().toString());
                    intent.putExtra("IMG" , url);
                    Toast.makeText(firestoreRycycler.this, url, Toast.LENGTH_SHORT).show();
                    startActivity(intent);



                }
            });





        }
    }


    @Override
    protected void onStart() {
        super.onStart();

        if (adapter != null)
            adapter.startListening();


    }

    @Override
    protected void onStop() {
        super.onStop();

        if (adapter != null)
            adapter.stopListening();


    }


}
