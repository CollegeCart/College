package com.example.collegecart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    EditText phoneNUm ;
    TextView countryCode;
    String countryC , phoneNumber;
    TextView error;
    FirebaseAuth auth;
    SpinKitView spinKitView;
    Button generateotp;
    FirebaseUser user;
    private  PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);
        countryCode = findViewById(R.id.countryCode);
        phoneNUm  =findViewById(R.id.phonenumber);
        generateotp = findViewById(R.id.LoginId);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        spinKitView = findViewById(R.id.generateSpin);
        error = findViewById(R.id.errrortext);
        spinKitView.setVisibility(View.GONE);

        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {



                   signInWithPhoneAuthCredential(phoneAuthCredential);


            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {



            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);

                spinKitView.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "Code Sent", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this , otpcode.class);
                intent.putExtra("AuthCredential" , s);
                startActivity(intent);

                finish();
            }
        };





    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (user != null)
        {
            Intent intent = new Intent(this , firestoreRycycler.class);
            startActivity(intent);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            finish();
        }

    }



    public void otp(View view) {




        phoneNumber = String.valueOf(phoneNUm.getText());
        String fullNo ="+91" + phoneNumber;

        if (phoneNumber.isEmpty() || phoneNumber.length() != 10) {
            error.setText("***Invalid Phone Number");
        }
        else {


            spinKitView.setVisibility(View.VISIBLE);
                if(isInternetConnection()) {
                    generateotp.setEnabled(false);
                    generateotp.setAlpha(.8f);
                    spinKitView.setVisibility(View.VISIBLE);
                    PhoneAuthProvider.getInstance().verifyPhoneNumber(fullNo, 60, TimeUnit.SECONDS, this, callbacks);
                }
                else
                    Toast.makeText(this, "No  Internet Connection", Toast.LENGTH_SHORT).show();


        }
    }

    public void skip(View view) {
        Intent intent =new Intent(this,firestoreRycycler.class);
        startActivity(intent);
        finish();


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




    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(MainActivity.this, "Registration Successfull", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(MainActivity.this , firestoreRycycler.class);
                            startActivity(intent);
                            finish();

                        } else {
                            // Sign in failed, display a message and update the UI
                            Toast.makeText(MainActivity.this, "Error Ocurred", Toast.LENGTH_SHORT).show();
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {

                                Toast.makeText(MainActivity.this, "Enter Code Is  Not Valid", Toast.LENGTH_SHORT).show();
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }






}
