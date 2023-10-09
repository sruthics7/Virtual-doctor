package com.example.virtualdoc;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Schedule extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView lv1;
    String slist="";
    SharedPreferences sp;
    String url="",ip="";
    String sdid;
    String scheduleid;
    ArrayList<String> sid,date,ft,tt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        lv1=findViewById(R.id.lv1);
        sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        RequestQueue queue = Volley.newRequestQueue(Schedule.this);
        url ="http://"+sp.getString("ip", "") + ":5000/viewshedule";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {

                    JSONArray ar=new JSONArray(response);
                    sid= new ArrayList<>();
                    date= new ArrayList<>();
                    ft= new ArrayList<>();
                    tt= new ArrayList<>();


                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);
                        sid.add(jo.getString("schedule_id"));
                        date.add(jo.getString("date"));
                        ft.add(jo.getString("from_time"));
                        tt.add(jo.getString("to_time"));

                    }

                    // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                    //lv.setAdapter(ad);

                    lv1.setAdapter(new custom3(Schedule.this,date,ft,tt));
                    lv1.setOnItemClickListener(Schedule.this);

                } catch (Exception e) {
                    Toast.makeText(Schedule.this, "exp"+e, Toast.LENGTH_SHORT).show();

                    Log.d("=========", e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Schedule.this, "err"+error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("did",getIntent().getStringExtra("did"));
                return params;
            }
        };
        queue.add(stringRequest);


    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        AlertDialog.Builder ald=new AlertDialog.Builder(Schedule.this);
        ald.setTitle("Select option")
                .setPositiveButton(" Book Doctor ", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        Intent in=new Intent(getApplicationContext(),paymentdoc.class);
                        in.putExtra("sid",sid.get(i));
                        startActivity(in);

                    }
                })
                .setNegativeButton("Cancel ", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent i=new Intent(getApplicationContext(),HOME.class);
                        startActivity(i);

                    }
                });

        AlertDialog al=ald.create();
        al.show();




    }
}