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
public class Pharmacies extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView lv1;
    SharedPreferences sp;
    String url="",ip="",bid;
    String scheduleid;
    ArrayList<String> pid,name,place,phn,email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacies);
        lv1=findViewById(R.id.lv5);
        sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        generate_bill();
            RequestQueue queue = Volley.newRequestQueue(Pharmacies.this);
        url ="http://"+sp.getString("ip", "") + ":5000/viewpharmacies";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {

                    JSONArray ar=new JSONArray(response);
                    pid= new ArrayList<>();
                    name= new ArrayList<>();
                    place= new ArrayList<>();
                    phn= new ArrayList<>();


                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);
                        pid.add(jo.getString("login_id"));
                        name.add(jo.getString("name"));
                        place.add(jo.getString("place"));
                        phn.add(jo.getString("phone"));
                    }

                    // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                    //lv.setAdapter(ad);

                    lv1.setAdapter(new custom3(Pharmacies.this,name,place,phn));
                    lv1.setOnItemClickListener(Pharmacies.this);

                } catch (Exception e) {
                    Toast.makeText(Pharmacies.this, "exp"+e, Toast.LENGTH_SHORT).show();

                    Log.d("=========", e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Pharmacies.this, "err"+error, Toast.LENGTH_SHORT).show();
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

    private void generate_bill() {
        RequestQueue queue = Volley.newRequestQueue(Pharmacies.this);
        url = "http://" + sp.getString("ip", "") + ":5000/cart";

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
//                        SharedPreferences.Editor edd=sp.edit();
//                        edd.putString("bill_id",res);
//                        edd.commit();
//                        Toast.makeText(Pharmacies.this, res, Toast.LENGTH_SHORT).show();

//                        Intent ik = new Intent(getApplicationContext(), HOME.class);
//                        startActivity(ik);


                    } else {

//                        Toast.makeText(paymentdoc.this, "Booking Failed", Toast.LENGTH_SHORT).show();
//                        Intent ik = new Intent(getApplicationContext(), HOME.class);
//                        startActivity(ik);


                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "exp" + e, Toast.LENGTH_LONG).show();

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
                params.put("lid", sp.getString("lid", ""));
                return params;

            }
        };
        queue.add(stringRequest);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent in=new Intent(getApplicationContext(),medicines.class);

        String ur ="http://"+sp.getString("ip","")+":5000/bill";
        // Instantiate the RequestQueue.
        RequestQueue queue1 = Volley.newRequestQueue(Pharmacies.this);
        // Request a string response from the provided URL.
        StringRequest stringRequest1 = new StringRequest(Request.Method.POST, ur,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {
                    JSONArray ar=new JSONArray(response);
                    JSONObject jo=ar.getJSONObject(0);
                    bid=jo.getString("max(bill_id)");
//                    Toast.makeText(getApplicationContext(),bid,Toast.LENGTH_LONG).show();
                    SharedPreferences.Editor edd=sp.edit();
                    edd.putString("bid",bid);
                    edd.commit();
//                    l1.setAdapter(new Custom3(Home.this,cmp,date,reply));
//                    ArrayAdapter<String> ad=new ArrayAdapter<String>(ViewComplaintReply.this,android.R.layout.simple_list_item_1,name);
//                    l1.setAdapter(ad)
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"",Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("lid", sp.getString("lid", ""));
                return params;
            }
        };
        // Add the request to the RequestQueue.
        queue1.add(stringRequest1);
//        in.putExtra("pid",pid.get(i));
        SharedPreferences.Editor edp=sp.edit();
        edp.putString("pid",pid.get(i));
        edp.commit();
        startActivity(in);
    }
}