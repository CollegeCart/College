package com.example.collegecart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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

public class myProducts extends AppCompatActivity {


    private   FirebaseFirestore firebaseFirestore;
    private RecyclerView recyclerView;

    FirestoreRecyclerAdapter adapter;
    FirebaseFirestore db;
    private  CircleImageView imageToolbar;
    private FirebaseUser user;
    String profileurl;
    TextView imageView;
    TextView title;

    ImageView searchItem;
    private ImageView favorites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_products);


        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.toolbaaar);

        View view = getSupportActionBar().getCustomView();
            user =  FirebaseAuth.getInstance().getCurrentUser();
            db = FirebaseFirestore.getInstance();
            searchItem = findViewById(R.id.searchProducts);
            searchItem.setVisibility(View.GONE);

        favorites = view.findViewById(R.id.SaveTofavorites);
        favorites.setVisibility(View.GONE);


            title = view.findViewById(R.id.ti);
            title.setText("My Products");




           imageView = view.findViewById(R.id.addItemToolbar);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(myProducts.this , sell.class );
                    startActivity(intent);

                }
            });





            recyclerView = findViewById(R.id.myProductsRecycler);
            firebaseFirestore = FirebaseFirestore.getInstance();


            Query query = firebaseFirestore.collection("Users").document(user.getUid()).collection("Products");


            FirestoreRecyclerOptions<ProductsModel> options = new FirestoreRecyclerOptions.Builder<ProductsModel>()
                    .setQuery(query , ProductsModel.class).build();


            adapter = new FirestoreRecyclerAdapter<ProductsModel,ProductsHolder>(options) {
                @NonNull
                @Override
                public ProductsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {



                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.buyproducts ,  parent , false);
                    return new ProductsHolder(view);
                }

                @Override
                protected void onBindViewHolder(@NonNull ProductsHolder holder, int position, @NonNull ProductsModel model) {


                    holder.subject.setText(model.getSubject());
                    holder.year.setText(model.getYear());
                    holder.price.setText(model.getPrice());
                    holder.branch.setText(model.getBranch());
                    Glide.with(myProducts.this).load(model.getImgUrl()).into(holder.image);



                }
            };

            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(adapter);

        }




    public class ProductsHolder extends  RecyclerView.ViewHolder{

        private TextView branch;

        private TextView year;

        private TextView subject;
        private ImageView image;
        LinearLayout linearLayout;
        TextView price;



        public ProductsHolder(@NonNull View itemView) {
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

                    Toast.makeText(myProducts.this, branch.getText().toString() + year.getText().toString() + subject.getText().toString(), Toast.LENGTH_SHORT).show();

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
