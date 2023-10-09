package com.example.virtualdoc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class bankpayment extends AppCompatActivity {
    EditText e1,e2,e3;
    Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bankpayment);
        e1=(EditText)findViewById(R.id.editTextTextPersonName9);
        e2=(EditText)findViewById(R.id.editTextTextPersonName11);
        e3=(EditText)findViewById(R.id.editTextTextPersonName12);
        b1=findViewById(R.id.button17);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String bank=e1.getText().toString();
                final String account=e2.getText().toString();
                final String ifsc=e3.getText().toString();



            }
        });
    }
}