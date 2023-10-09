package com.example.virtualdoc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import java.util.ArrayList;

public class viewreply extends AppCompatActivity {
    ListView l1;
    SharedPreferences sp;
    String url="",ip="";
    ArrayList<String> date,complaint,reply;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewreply);    
        l1=findViewById(R.id.list1);
        sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        RequestQueue queue = Volley.newRequestQueue(viewreply.this);
        url ="http://"+sp.getString("ip", "") + ":5000/viewreply";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {

                    JSONArray ar=new JSONArray(response);
                    date= new ArrayList<>();
                    complaint= new ArrayList<>();
                    reply= new ArrayList<>();


                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);
                        date.add(jo.getString("date"));
                        complaint.add(jo.getString("complaint"));
                        reply.add(jo.getString("reply"));

                    }

                    // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                    //lv.setAdapter(ad);

                   l1.setAdapter(new custom3(viewreply.this,date,complaint,reply));
//                    l1.setOnItemClickListener(viewreply.this);

                } catch (Exception e) {
                    Toast.makeText(viewreply.this, "exp"+e, Toast.LENGTH_SHORT).show();

                    Log.d("=========", e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(viewreply.this, "err"+error, Toast.LENGTH_SHORT).show();
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