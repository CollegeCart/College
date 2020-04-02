package com.example.collegecart;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class contacts extends AppCompatActivity {




    FirebaseFirestore database;
    FirebaseUser user;
    RadioButton mobile , eemail;
    EditText emailText , numberText;
    FirebaseFirestore getDatabase;
    RadioGroup radioGroup;

    Button saveContacts;
    private TextView title;
    private ImageView userImage;
    Map<String , String> contact;
    private ImageView favorites;
    private TextView addItems;
    private ImageView searchItems;
    private ImageView sharebutton;
    private ImageView deleteProducts;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);


        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.toolbaaar);

        View view = getSupportActionBar().getCustomView();


        userImage = view.findViewById(R.id.imageToolbar);
        userImage.setVisibility(View.GONE);
        title = view.findViewById(R.id.ti);
        numberText = findViewById(R.id.numbertext);
        emailText = findViewById(R.id.emailText);
        sharebutton = view.findViewById(R.id.shareButton);
        sharebutton.setVisibility(View.GONE);

        getDatabase = FirebaseFirestore.getInstance();
        mobile = findViewById(R.id.mobileradio);
        eemail = findViewById(R.id.emailradio);
        title.setText("Contact Info");
        favorites = view.findViewById(R.id.SaveTofavorites);
        addItems = view.findViewById(R.id.addItemToolbar);
        addItems.setVisibility(View.GONE);
        favorites.setVisibility(View.GONE);
        searchItems = view.findViewById(R.id.searchProducts);
        searchItems.setVisibility(View.GONE);



        deleteProducts = view.findViewById(R.id.deleteProducts);
        deleteProducts.setVisibility(View.GONE);










           contact = new HashMap<>();



        database =FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        database.collection("Users").document(user.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                numberText.setText(documentSnapshot.getString("Number"));
                emailText.setText(documentSnapshot.getString("Email"));

            }
        });
        radioGroup = findViewById(R.id.radioGroup2);


        saveContacts = findViewById(R.id.saveContacts);

        SharedPreferences preferences = getSharedPreferences("MYPREF" , MODE_PRIVATE);
      int Checked = preferences.getInt("state" , 0);

      if (eemail.getId() == Checked)
      {
          eemail.setChecked(true);

      }
      if (mobile.getId() == Checked)
          mobile.setChecked(true);




        saveContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int a =  radioGroup.getCheckedRadioButtonId();

                SharedPreferences preferences = getSharedPreferences("MYPREF" , MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("state" , a);
                editor.commit();


                if (eemail.getId() == a)
                {

                    getDatabase.collection("Users").document(user.getUid()).update("Contact" , emailText.getText().toString());


                }
                if (mobile.getId() == a)
                {

                    getDatabase.collection("Users").document(user.getUid()).update("Contact" , numberText.getText().toString());

                }





                getDatabase.collection("Users").document(user.getUid()).update("Number" , numberText.getText().toString());

                getDatabase.collection("Users").document(user.getUid()).update("Email" , emailText.getText().toString());



                numberText.setText(numberText.getText().toString());
                emailText.setText(emailText.getText().toString());
                Toast.makeText(contacts.this, "Contact Info Saved", Toast.LENGTH_SHORT).show();



            }
        });







    }
}
