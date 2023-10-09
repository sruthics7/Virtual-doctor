package com.example.virtualdoc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class NEWUSER extends AppCompatActivity {
    EditText e1,e2,e3,e4,e5,e6,e7,e8,e9;
    RadioButton r1,r2,r3;
    Button b1;
    String fname,lname,gender,dob,place,post,pin,phone,uname,password;
    String url;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_n_e_w_u_s_e_r);
        sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        e1=findViewById(R.id.editTextTextPersonName3);
        e2=findViewById(R.id.editTextTextPersonName4);
        e3=findViewById(R.id.editTextDate);
        e4=findViewById(R.id.editTextTextPersonName5);
        e5=findViewById(R.id.editTextTextPersonName6);
        e6=findViewById(R.id.editTextNumber);
        e7=findViewById(R.id.editTextNumber2);
        e8=findViewById(R.id.editTextTextPersonName10);
        e9=findViewById(R.id.editTextNumberPassword);
        r1=findViewById(R.id.radioButton);
        r2=findViewById(R.id.radioButton3);
        r3=findViewById(R.id.radioButton2);
        b1=findViewById(R.id.button11);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fname=e1.getText().toString();
                lname=e2.getText().toString();
                if(r1.isChecked())
                {
                    gender=r1.getText().toString();
                }
                else if(r2.isChecked())
                {
                    gender=r2.getText().toString();
                }
                else
                {
                    gender=r3.getText().toString();
                }
                dob=e3.getText().toString();
                place=e4.getText().toString();
                post=e5.getText().toString();
                pin=e6.getText().toString();
                phone=e7.getText().toString();
                uname=e8.getText().toString();
                password=e9.getText().toString();

                if(fname.equalsIgnoreCase(""))
                {
                    e1.setError("enter fname");

                    e1.requestFocus();
                }

                else if(!fname.matches("^[a-zA-Z]*$"))
                {
                    e1.setError("characters allowed");
                    e1.requestFocus();
                }

                else if (lname.equalsIgnoreCase("")) {
                    e2.setError("enter lname");
                    e2.requestFocus();
                }
                else if(!lname.matches("^[a-zA-Z]*$"))
                {
                    e2.setError("characters allowed");
                    e2.requestFocus();
                }
                else if (dob.equalsIgnoreCase("")) {
                    e3.setError("enter dob");
                    e3.requestFocus();
                }

                else if (place.equalsIgnoreCase("")) {
                    e4.setError("enter place");
                    e4.requestFocus();
                }
                else if(!place.matches("^[a-zA-Z]*$"))
                {
                    e4.setError("characters allowed");
                    e4.requestFocus();
                }
                    else if (post.equalsIgnoreCase("")) {
                    e5.setError("enter post");
                    e5.requestFocus();
                }

                else if (pin.equalsIgnoreCase("")) {
                    e6.setError("enter pin");
                    e6.requestFocus();
                }
                else if(pin.length()!=6)
                {
                    e6.setError("Invalid pin");
                    e6.requestFocus();
                }
                else if (phone.equalsIgnoreCase("")) {
                    e7.setError("enter phone");
                    e7.requestFocus();
                }
                else if(phone.length()!=10)
                {
                    e7.setError("Invalid phoneno");
                    e7.requestFocus();
                }
                else if (uname.equalsIgnoreCase("")) {
                    e8.setError("enter uname");
                    e8.requestFocus();
                }

                else if (password.equalsIgnoreCase("")) {
                    e9.setError("enter password");
                    e9.requestFocus();
                }

                else {

                    RequestQueue queue = Volley.newRequestQueue(NEWUSER.this);
                    url = "http://" + sp.getString("ip", "") + ":5000/registration";

                    // Request a string response from the provided URL.
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the response string.
                            Log.d("+++++++++++++++++", response);
                            try {
                                JSONObject json = new JSONObject(response);
                                String res = json.getString("task");

                                if (res.equalsIgnoreCase("success")) {


                                    Toast.makeText(NEWUSER.this, "registration successfull", Toast.LENGTH_SHORT).show();
                                    Intent ik = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(ik);


                                } else {

                                    Toast.makeText(NEWUSER.this, "registration failed", Toast.LENGTH_SHORT).show();
                                    Intent ik = new Intent(getApplicationContext(), HOME.class);
                                    startActivity(ik);


                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {


                            Toast.makeText(getApplicationContext(), "Error" + error, Toast.LENGTH_LONG).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("fnm", fname);
                            params.put("lname", lname);
                            params.put("gender", gender);
                            params.put("dob", dob);
                            params.put("place", place);
                            params.put("post", post);
                            params.put("pin", pin);
                            params.put("phone", phone);
                            params.put("uname", uname);
                            params.put("pass", password);

                            return params;
                        }
                    };
                    queue.add(stringRequest);
                }
            }
        });
    }
}