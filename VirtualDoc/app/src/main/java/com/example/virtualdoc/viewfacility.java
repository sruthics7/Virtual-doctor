package com.example.virtualdoc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

public class viewfacility extends AppCompatActivity implements AdapterView.OnItemClickListener {
 ListView lv1;
 String slist="";
    SharedPreferences sp;
    String url="",ip="";
    String sdocid;
    ArrayList<String> did,name,hosp,department;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewfacility);
        lv1=findViewById(R.id.lv1);
        slist=getIntent().getStringExtra("sl");
        sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        RequestQueue queue = Volley.newRequestQueue(viewfacility.this);
        url ="http://"+sp.getString("ip", "") + ":5000/viewfacility";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {

                    JSONArray ar=new JSONArray(response);

                    name= new ArrayList<>();

                    department= new ArrayList<>();


                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);

                        name.add(jo.getString("Facility"));

                        department.add(jo.getString("Description"));

                    }

                    // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                    //lv.setAdapter(ad);

                    lv1.setAdapter(new custom2(viewfacility.this,name,department));
                    lv1.setOnItemClickListener(viewfacility.this);

                } catch (Exception e) {
                    Toast.makeText(viewfacility.this, "exp"+e, Toast.LENGTH_SHORT).show();

                    Log.d("=========", e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(viewfacility.this, "err"+error, Toast.LENGTH_SHORT).show();
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
        Intent i=new Intent(getApplicationContext(),viewdocshedule.class);
        i.putExtra("did",sdocid);
        startActivity(i);
    }
}