package com.example.virtualdoc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class doctors extends AppCompatActivity implements AdapterView.OnItemClickListener,AdapterView.OnItemSelectedListener {
    ListView lv1;
    SharedPreferences sp;
    String url="",ip="",hid;
    Spinner s;
    Button b;
    ArrayList<String> did,name,hosp,hosid,department,phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors);
        lv1=findViewById(R.id.llv2);
        s=findViewById(R.id.spinner4);
        b=findViewById(R.id.button22);
        sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        RequestQueue queue = Volley.newRequestQueue(doctors.this);
        String url1 ="http://"+sp.getString("ip", "") + ":5000/viewhosp";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url1,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {

                    JSONArray ar=new JSONArray(response);
                    hosid= new ArrayList<>();
                    hosp= new ArrayList<>();


                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);
                        hosid.add(jo.getString("lid"));
                        hosp.add(jo.getString("name"));

                    }

                     ArrayAdapter<String> ad=new ArrayAdapter<String>(doctors.this,android.R.layout.simple_list_item_1,hosp);
                    s.setAdapter(ad);
                    s.setOnItemSelectedListener(doctors.this);


                } catch (Exception e) {
                    Toast.makeText(doctors.this, "exp"+e, Toast.LENGTH_SHORT).show();

                    Log.d("=========", e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(doctors.this, "err"+error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };
        queue.add(stringRequest);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RequestQueue queue = Volley.newRequestQueue(doctors.this);
                url ="http://"+sp.getString("ip", "") + ":5000/vwdoc";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the response string.
                        Log.d("+++++++++++++++++",response);
                        try {

                            JSONArray ar=new JSONArray(response);
                            did= new ArrayList<>();
                            name= new ArrayList<>();
                            phone= new ArrayList<>();
                            department= new ArrayList<>();


                            for(int i=0;i<ar.length();i++)
                            {
                                JSONObject jo=ar.getJSONObject(i);
                                did.add(jo.getString("log_id"));
                                name.add(jo.getString("fname")+" "+jo.getString("lname"));
                                phone.add(jo.getString("phone_no"));
                                department.add(jo.getString("departmnt"));

                            }

                            // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                            //lv.setAdapter(ad);

                            lv1.setAdapter(new custom3(doctors.this,name,department,phone));
                            lv1.setOnItemClickListener(doctors.this);

                        } catch (Exception e) {
                            Toast.makeText(doctors.this, "exp"+e, Toast.LENGTH_SHORT).show();

                            Log.d("=========", e.toString());
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(doctors.this, "err"+error, Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("hid",hid);
                        return params;
                    }
                };
                queue.add(stringRequest);


            }
        });

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        Intent im=new Intent(getApplicationContext(),Schedule.class);
        im.putExtra("did",did.get(i));
        startActivity(im);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
      hid=hosid.get(i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}