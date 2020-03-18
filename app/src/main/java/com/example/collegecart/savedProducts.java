package com.example.collegecart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import de.hdodenhof.circleimageview.CircleImageView;

public class savedProducts extends AppCompatActivity {




    FirestoreRecyclerAdapter adapter;
    FirebaseFirestore db;
    CircleImageView imageToolbar;
    private FirebaseUser user;
    String profileurl;
    RecyclerView recyclerView;
    FirebaseFirestore firebaseFirestore;
    private TextView title;
    ImageView searchProducts;
    TextView additem;
    String Subject;
    private ImageView favorites;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_products);





        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.toolbaaar);

        Intent intent =  getIntent();
        Subject = intent.getStringExtra("Subject");
        View view = getSupportActionBar().getCustomView();
        title = view.findViewById(R.id.ti);
        title.setText("Search Results");


        favorites = view.findViewById(R.id.SaveTofavorites);
        user = FirebaseAuth.getInstance().getCurrentUser();
        favorites.setVisibility(View.GONE);

        additem = findViewById(R.id.addItemToolbar);
        additem.setVisibility(View.GONE);
        recyclerView = findViewById(R.id.savedRecycler);
        searchProducts = findViewById(R.id.searchProducts);
        searchProducts.setVisibility(View.GONE);
        firebaseFirestore = FirebaseFirestore.getInstance();



        Query query = firebaseFirestore.collection("Users").document(user.getUid()).collection("Favorites");

        FirestoreRecyclerOptions<ProductsModel> options = new FirestoreRecyclerOptions.Builder<ProductsModel>()
                .setQuery(query , ProductsModel.class).build();


        adapter = new FirestoreRecyclerAdapter<ProductsModel, Prodlder>(options) {
            @NonNull
            @Override
            public Prodlder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {



                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.buyproducts ,  parent , false);
                return new Prodlder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull Prodlder holder, int position, @NonNull ProductsModel model) {


                holder.subject.setText(model.getSubject());
                holder.year.setText(model.getYear());
                holder.branch.setText(model.getBranch());
                holder.price.setText(model.getPrice());
                Glide.with(savedProducts.this).load(model.getImgUrl()).into(holder.image);



            }
        };

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);






    }



    public class Prodlder extends  RecyclerView.ViewHolder{

        private TextView branch;

        private TextView year;
        TextView price;

        private TextView subject;
        private ImageView image;
        LinearLayout linearLayout;



        public Prodlder(@NonNull View itemView) {
            super(itemView);

            branch = itemView.findViewById(R.id.BranchRecycler);
            year =itemView.findViewById(R.id.yearRecycler);
            subject = itemView.findViewById(R.id.subjectReycler);
            linearLayout = itemView.findViewById(R.id.recycleLayout);
            price = itemView.findViewById(R.id.pricerecycler);
            image= itemView.findViewById(R.id.RecyclerImage);
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(savedProducts.this, branch.getText().toString() + year.getText().toString() + subject.getText().toString(), Toast.LENGTH_SHORT).show();

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
