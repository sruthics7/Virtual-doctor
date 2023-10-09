package com.example.virtualdoc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class viewdr extends AppCompatActivity implements AdapterView.OnItemClickListener {
 ListView lv1;
 String slist="";
    SharedPreferences sp;
    String url="",ip="";
    String sdocid;
    ArrayList<String> did,name,hosp,department;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewdr);
        lv1=findViewById(R.id.lv1);
        slist=getIntent().getStringExtra("sl");
        sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        RequestQueue queue = Volley.newRequestQueue(viewdr.this);
        url ="http://"+sp.getString("ip", "") + ":5000/VIEWDOC";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {

                    JSONArray ar=new JSONArray(response);

                    name= new ArrayList<>();
                    did= new ArrayList<>();

                    department= new ArrayList<>();


                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);

                        name.add(jo.getString("Fname")+" "+jo.getString("Lname"));
                        did.add(jo.getString("Login_id"));

                        department.add(jo.getString("Specification"));

                    }

                    // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                    //lv.setAdapter(ad);

                    lv1.setAdapter(new custom2(viewdr.this,name,department));
                    lv1.setOnItemClickListener(viewdr.this);

                } catch (Exception e) {
                    Toast.makeText(viewdr.this, "exp"+e, Toast.LENGTH_SHORT).show();

                    Log.d("=========", e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(viewdr.this, "err"+error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                return params;
            }
        };
        queue.add(stringRequest);





    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
sdocid=did.get(position);

        RequestQueue queue = Volley.newRequestQueue(viewdr.this);
        url = "http://" + sp.getString("ip","") + ":5000/bookdoc";

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


                        Toast.makeText(viewdr.this, "success", Toast.LENGTH_SHORT).show();
                        Intent ik = new Intent(getApplicationContext(), HOME.class);
                        startActivity(ik);


                    } else {




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
                params.put("did", sdocid);
                params.put("uid", sp.getString("lid",""));

                return params;
            }
        };
        queue.add(stringRequest);






    }
}