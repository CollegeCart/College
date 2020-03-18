package com.example.collegecart;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class contacts extends AppCompatActivity {



    EditText mobileNumber;
    EditText Email;
    FirebaseFirestore database;
    FirebaseUser user;
    RadioGroup radioGroup;

    Button saveContacts;
    private TextView title;
    private ImageView userImage;
    private ImageView favorites;
    private TextView addItems;
    private ImageView searchItems;

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
        title.setText("Contact Info");
        favorites = view.findViewById(R.id.SaveTofavorites);
        addItems = view.findViewById(R.id.addItemToolbar);
        addItems.setVisibility(View.GONE);
        favorites.setVisibility(View.GONE);
        searchItems = view.findViewById(R.id.searchProducts);
        searchItems.setVisibility(View.GONE);









        mobileNumber =findViewById(R.id.editText2);
        Email = findViewById(R.id.editText3);
        database =FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        database.collection("Users").document(user.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                mobileNumber.setText(documentSnapshot.getString("Number"));
                Email.setText(documentSnapshot.getString("Email"));

            }
        });
        radioGroup = findViewById(R.id.radioGroup2);

        saveContacts = findViewById(R.id.saveContacts);








    }
}
