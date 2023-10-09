package com.example.virtualdoc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

public class virtualdoctor extends AppCompatActivity {
    Button b1;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_virtualdoctor);
        list=findViewById(R.id.list1);
        b1=findViewById(R.id.button12);
    }
}