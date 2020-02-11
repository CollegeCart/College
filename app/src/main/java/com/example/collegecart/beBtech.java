package com.example.collegecart;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class beBtech extends AppCompatActivity {

    String[] string = {"First" , "Second" , "Third","Fourth" ,"Fifth" ,"Sixth" , "Seventh" , "Eighth"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.abc_list_menu_item_layout);


        Spinner spinner = findViewById(R.id.spiinner);
        ArrayAdapter a = new ArrayAdapter(this , android.R.layout.simple_spinner_item , string );
        a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(a);
    }


}
