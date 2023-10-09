package com.example.virtualdoc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
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

public class VIEWPRISCRIPTION extends AppCompatActivity implements AdapterView.OnItemClickListener {
    EditText e1;
    ListView l1;
    SharedPreferences sp;
    String url="",ip="";
    ArrayList<String> date,status,fname,lname,bid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_v_i_e_w_p_r_i_s_c_r_i_p_t_i_o_n);
        l1=findViewById(R.id.list1);


        sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        RequestQueue queue = Volley.newRequestQueue(VIEWPRISCRIPTION.this);
        url ="http://"+sp.getString("ip", "") + ":5000/viewp";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {

                    JSONArray ar=new JSONArray(response);
                    date= new ArrayList<>();
                    status= new ArrayList<>();
                    fname= new ArrayList<>();
                    lname=new ArrayList<>();
                    bid=new ArrayList<>();


                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);
                        date.add(jo.getString("date"));
                        bid.add(jo.getString("id"));
                        status.add(jo.getString("status"));
                        fname.add(jo.getString("fname")+" "+jo.getString("lname"));
                        lname.add(jo.getString("lname"));

                    }

                    // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                    //lv.setAdapter(ad);

                    l1.setAdapter(new custom3(VIEWPRISCRIPTION.this,date,status,fname));
                    l1.setOnItemClickListener(VIEWPRISCRIPTION.this);

                } catch (Exception e) {
                    Toast.makeText(VIEWPRISCRIPTION.this, "exp"+e, Toast.LENGTH_SHORT).show();

                    Log.d("=========", e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(VIEWPRISCRIPTION.this, "err"+error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("lid",sp.getString("lid",""));
                return params;
            }
        };
        queue.add(stringRequest);




    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent i=new Intent(getApplicationContext(),addprescription.class);
        i.putExtra("bid",bid.get(position));
        startActivity(i);
    }
}