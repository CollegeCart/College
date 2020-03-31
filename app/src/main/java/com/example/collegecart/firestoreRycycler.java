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
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Slide;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class firestoreRycycler extends AppCompatActivity {

 private   FirebaseFirestore firebaseFirestore;
    private RecyclerView recyclerView;

    FirestoreRecyclerAdapter adapter;
    FirebaseFirestore db;
    ImageView favorites;
    CircleImageView imageToolbar;
    private FirebaseUser user;
    String gender;
    String profileurl;

    SpinKitView  spinKitView;
    ImageView sharebutton;
    ImageView deleteProducts;
    private TextView title;
    ImageView searchProducts;
    Query query;
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
        recyclerView = findViewById(R.id.ryclerId);

        recyclerView.setVisibility(View.GONE);

        title.setText("Products");
        additem = view.findViewById(R.id.addItemToolbar);
        sharebutton = view.findViewById(R.id.shareButton);
        sharebutton.setVisibility(View.GONE);
        additem.setVisibility(View.GONE);
        spinKitView = findViewById(R.id.firespin);
        spinKitView.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                recyclerView.setVisibility(View.VISIBLE);

                spinKitView.setVisibility(View.GONE);
            }
        },2000);


        deleteProducts = view.findViewById(R.id.deleteProducts);
        deleteProducts.setVisibility(View.GONE);
        favorites = view.findViewById(R.id.SaveTofavorites);
        favorites.setVisibility(View.GONE);




        imageToolbar = view.findViewById(R.id.imageToolbar);
        imageToolbar.setVisibility(View.GONE);
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





        imageToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user !=null)
                {
                    Intent intent = new Intent(firestoreRycycler.this , Main3Activity.class);
                    intent.putExtra("Gender" , gender);
                    startActivity(intent);
                }
                else {

                    Intent intent = new Intent(firestoreRycycler.this , MainActivity.class);
                    startActivity(intent);
                    finish();

                }
            }
        });


        firebaseFirestore = FirebaseFirestore.getInstance();

      if(user != null)
      {

       new Handler().postDelayed(new Runnable() {
           @Override
           public void run() {
               firebaseFirestore.collection("Users").document(user.getUid()).get().addOnSuccessListener(
                       new OnSuccessListener<DocumentSnapshot>() {
                           @Override
                           public void onSuccess(DocumentSnapshot documentSnapshot) {
                               gender = documentSnapshot.getString("Gender");
                               if (gender.equals("Female"))
                               {
                                   Glide.with(firestoreRycycler.this).load(R.mipmap.female).into(imageToolbar);
                               }
                               else
                               {

                                   Glide.with(firestoreRycycler.this).load(R.mipmap.male).into(imageToolbar);

                               }
                               imageToolbar.setVisibility(View.VISIBLE);

                           }
                       }
               );
           }
       },1000);



       new Handler().postDelayed(new Runnable() {
           @Override
           public void run() {




           }
       },2000);

      }

      if (isInternetConnection())
      {
          query = firebaseFirestore.collectionGroup("Products");



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

                  holder.url = model.getImgUrl();
                  holder.time = model.getTimestamp();
                  holder.branch.setText(model.getBranch() + model.getCategory());

                  holder.userame = model.getUsername();
                  holder.Category = model.getCategory();
                  holder.productname.setText(model.getProductname());

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
                          holder.subject.setText(model.getSubject());
                          holder.year.setText(model.getYear());

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



                  holder.price.setText(model.getPrice());
                  Glide.with(firestoreRycycler.this).load(model.getImgUrl()).into(holder.image);



              }
          };

          recyclerView.setLayoutManager(new LinearLayoutManager(this));

          recyclerView.setHasFixedSize(true);
          recyclerView.setAdapter(adapter);






      }
      else
      {
          Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
      }


      }







    public class ProductsViewHolder extends  RecyclerView.ViewHolder{

        private TextView branch;

        private TextView year;

        private TextView subject;
        TextView price;
        private ImageView image;
        String url;
        LinearLayout linearLayout;
        TextView productname;
        String time;
        String userID;
        String Category;
        String userame;



        public ProductsViewHolder(@NonNull View itemView) {
            super(itemView);



            branch = itemView.findViewById(R.id.BranchRecycler);
            year =itemView.findViewById(R.id.yearRecycler);
            subject = itemView.findViewById(R.id.subjectReycler);
            price = itemView.findViewById(R.id.pricerecycler);
            productname = itemView.findViewById(R.id.recuclerproductname);

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
                    intent.putExtra("ID" , userID);
                    intent.putExtra("category" , Category);
                    intent.putExtra("time" , time);
                    intent.putExtra("from" , "firestore");
                    intent.putExtra("username" , userame);
                    intent.putExtra("productname" , productname.getText().toString());
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
