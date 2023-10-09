package com.example.virtualdoc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ipAddress extends AppCompatActivity {
    EditText e1;
    Button b1;
    String ip;
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ip_address);
        e1=findViewById(R.id.editTextTextPersonName);
        b1=findViewById(R.id.button14);
        sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ip=e1.getText().toString();

                if(ip.equalsIgnoreCase(""))
                {
                    e1.setError("enter ipaddress");
                }
                else {
                    SharedPreferences.Editor ed = sp.edit();
                    ed.putString("ip", ip);
                    ed.commit();
                    Toast.makeText(ipAddress.this, ip, Toast.LENGTH_SHORT).show();


                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                }
            }
        });
    }
}