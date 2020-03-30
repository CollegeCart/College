package com.example.collegecart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

public class otpcode extends AppCompatActivity {


    private String credential;
    private EditText otptext;
    private FirebaseAuth mAuth;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_generate_otp);

        mAuth = FirebaseAuth.getInstance();



        otptext = findViewById(R.id.otpedit);
        button = findViewById(R.id.otpbuttuon);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String otp = otptext.getText().toString();
                credential =getIntent().getStringExtra("AuthCredential");
                PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(credential , otp );
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }
        });
    }





    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(otpcode.this, "Registration Successfull", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(otpcode.this , profile.class);
                            startActivity(intent);
                            finish();

                        } else {
                            // Sign in failed, display a message and update the UI
                            Toast.makeText(otpcode.this, "Error Ocurred", Toast.LENGTH_SHORT).show();
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verificTation code entered was invalid
                                Toast.makeText(otpcode.this, "Invalid Code", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }


    @Override
    protected void onPause() {


        Toast.makeText(this, "Do You Really Want  To Quit", Toast.LENGTH_SHORT).show();
        super.onPause();

    }
}
