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

public class complaint extends AppCompatActivity {
    EditText e1;
    Button b1;
    SharedPreferences sp;
    String complaint;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);
        sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        e1=findViewById(R.id.editTextTextPersonName7);
        b1=findViewById(R.id.button9);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                complaint=e1.getText().toString();
                if(complaint.equalsIgnoreCase(""))
                {
                    e1.setError("enter complaint");
                }
                else {
                    RequestQueue queue = Volley.newRequestQueue(complaint.this);
                    url = "http://" + sp.getString("ip", "") + ":5000/complaint";

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


                                    Toast.makeText(complaint.this, "send successfully", Toast.LENGTH_SHORT).show();
                                    Intent ik = new Intent(getApplicationContext(), HOME.class);
                                    startActivity(ik);


                                } else {

                                    Toast.makeText(complaint.this, "sending failed", Toast.LENGTH_SHORT).show();
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
                            params.put("com", complaint);
                            params.put("lid", sp.getString("lid", ""));

                            return params;

                        }
                    };
                    queue.add(stringRequest);
                }


            }
        });
    }
}