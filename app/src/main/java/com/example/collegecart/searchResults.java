package com.example.collegecart;

import androidx.annotation.Keep;
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
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import de.hdodenhof.circleimageview.CircleImageView;

public class searchResults extends AppCompatActivity {


    FirestoreRecyclerAdapter adapter;
    FirebaseFirestore db;
    CircleImageView imageToolbar;
    private FirebaseUser user;
    String profileurl;
    @Keep
    Query query;
    CircleImageView profilepic;
    RecyclerView recyclerView;
    SpinKitView spinKitView;
    ImageView sharebutton;
    FirebaseFirestore firebaseFirestore;
    @Keep
    private TextView title;
    ImageView searchProducts;
    @Keep
    TextView additem;
    ProgressBar progressBar;
    @Keep
    String Subject;
    private ImageView favorites;
    private ImageView deleteProducts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);




        spinKitView = findViewById(R.id.searchspin);
        spinKitView.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                spinKitView.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);

            }
        },1500);



        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.toolbaaar);

        Intent intent =  getIntent();
        Subject = intent.getStringExtra("Subject");
        View view = getSupportActionBar().getCustomView();
        title = view.findViewById(R.id.ti);
        title.setText("Search Results");

        deleteProducts = view.findViewById(R.id.deleteProducts);
        deleteProducts.setVisibility(View.GONE);

        sharebutton = view.findViewById(R.id.shareButton);
        sharebutton.setVisibility(View.GONE);
        favorites = view.findViewById(R.id.SaveTofavorites);
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
        recyclerView = findViewById(R.id.SearchRecycler);
        searchProducts = findViewById(R.id.searchProducts);
        searchProducts.setVisibility(View.GONE);
        firebaseFirestore = FirebaseFirestore.getInstance();
        recyclerView.setVisibility(View.GONE);


        if (intent.getStringExtra("category").equals("Btech"))
        {

            query = firebaseFirestore.collectionGroup("Products").whereEqualTo("subject" , Subject);

        }
        if (intent.getStringExtra("category").equals("Gate"))
        {

            query = firebaseFirestore.collectionGroup("Products").whereEqualTo("category" , intent.getStringExtra("category")).whereEqualTo("branch" , intent.getStringExtra("branch"));

        }

        if (intent.getStringExtra("category").equals("GRE") || intent.getStringExtra("category").equals("GMAT") )
        {

            query = firebaseFirestore.collectionGroup("Products").whereEqualTo("category" , intent.getStringExtra("category"));


        }







                    FirestoreRecyclerOptions<ProductsModel> options = new FirestoreRecyclerOptions.Builder<ProductsModel>()
                            .setQuery(query , ProductsModel.class).build();


                    adapter = new FirestoreRecyclerAdapter<ProductsModel, ProdHolder>(options) {
                        @NonNull
                        @Override
                        public ProdHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {



                            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.buyproducts ,  parent , false);
                            return new ProdHolder(view);
                        }

                        @Override
                        protected void onBindViewHolder(@NonNull ProdHolder holder, int position, @NonNull ProductsModel model) {


                            holder.subject.setText(model.getSubject());
                            holder.year.setText(model.getYear());
                            holder.branch.setText(model.getBranch());
                            holder.productname.setText(model.getProductname());
                            holder.price.setText(model.getPrice());
                            holder.url = model.getImgUrl();
                            holder.Category = model.getCategory();
                            holder.userame = model.getUsername();
                            holder.userID = model.getUserID();

                            switch (holder.Category)
                            {
                                case "Gate":
                                {
                                    holder.year.setVisibility(View.GONE);
                                    holder.subject.setVisibility(View.GONE);
                                    holder.branch.setText(model.getBranch() + " (" + model.getCategory() + ")");
                                }
                                break;
                                case "Btech":
                                {

                                    holder.branch.setText(model.getBranch() + " (" + model.getCategory() + ")");

                                }
                                break;
                                case "GRE":
                                {
                                    holder.subject.setVisibility(View.GONE);
                                    holder.year.setVisibility(View.GONE);
                                    holder.branch.setText(model.getBranch());
                                }
                                break;

                                case "GMAT":
                                {
                                    holder.year.setVisibility(View.GONE);
                                    holder.branch.setVisibility(View.GONE);
                                    holder.subject.setVisibility(View.GONE);

                                }
                                break;


                            }



                            Glide.with(searchResults.this).load(model.getImgUrl()).into(holder.image);



                        }
                    };

                    recyclerView.setLayoutManager(new LinearLayoutManager(searchResults.this));

                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(adapter);





    }



    public class ProdHolder extends  RecyclerView.ViewHolder{

        private TextView branch;

        private TextView year;
        TextView price;

        private TextView subject;
        private ImageView image;
        LinearLayout linearLayout;

        String userID;
        String userame;
        String url;
        String Category;

        TextView productname;


        public ProdHolder(@NonNull View itemView) {
            super(itemView);

            branch = itemView.findViewById(R.id.BranchRecycler);
            year =itemView.findViewById(R.id.yearRecycler);
            productname = itemView.findViewById(R.id.recuclerproductname);

            subject = itemView.findViewById(R.id.subjectReycler);
            linearLayout = itemView.findViewById(R.id.recycleLayout);
            price = itemView.findViewById(R.id.pricerecycler);
            image= itemView.findViewById(R.id.RecyclerImage);
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Intent intent = new Intent(searchResults.this , ProductsDetails.class);
                    intent.putExtra("Subject" , subject.getText().toString());
                    intent.putExtra("Year" , year.getText().toString());
                    intent.putExtra("productname" , productname.getText().toString());
                    intent.putExtra("Branch" , branch.getText().toString());
                    intent.putExtra("Price" , price.getText().toString());
                    intent.putExtra("IMG" , url);
                    intent.putExtra("from" , "search");
                    intent.putExtra("ID" , userID);
                    intent.putExtra("category" , Category);
                    intent.putExtra("username" , userame);
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
