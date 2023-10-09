package com.example.virtualdoc;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

public class viewbookingstatus extends AppCompatActivity {
    ListView l1;
    SharedPreferences sp;
    String url="",ip="";
    ArrayList<String> date,doctor,status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewbookingstatus);
        l1=findViewById(R.id.list1);
        sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        RequestQueue queue = Volley.newRequestQueue(viewbookingstatus.this);
        url ="http://"+sp.getString("ip", "") + ":5000/bkstatus";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {

                    JSONArray ar=new JSONArray(response);
                    date= new ArrayList<>();
                    doctor= new ArrayList<>();
                    status= new ArrayList<>();


                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);
                        date.add(jo.getString("date"));
                        doctor.add(jo.getString("Fname")+jo.getString("Lname"));
                        status.add(jo.getString("booking_status"));

                    }

                    // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                    //lv.setAdapter(ad);

                   l1.setAdapter(new custom3(viewbookingstatus.this,doctor,date,status));
//                    l1.setOnItemClickListener(viewreply.this);

                } catch (Exception e) {
                    Toast.makeText(viewbookingstatus.this, "exp"+e, Toast.LENGTH_SHORT).show();

                    Log.d("=========", e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(viewbookingstatus.this, "err"+error, Toast.LENGTH_SHORT).show();
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
}