package com.example.virtualdoc;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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
public class medicines extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView lv1;
    SharedPreferences sp;
    String url="",ip="",pid;
    ArrayList<String> mid,mname,des,price,pic,exp,dos;
    Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicines);
        lv1=findViewById(R.id.lv5);
        b=findViewById(R.id.button23);
        sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        pid=getIntent().getStringExtra("pid");

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RequestQueue queue = Volley.newRequestQueue(medicines.this);
                url = "http://" + sp.getString("ip","") + ":5000/bill_id";

                // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the response string.
                        Log.d("+++++++++++++++++", response);
                        try {
                            JSONObject json = new JSONObject(response);
                            String res = json.getString("task");

//                            if (res.equalsIgnoreCase("success")) {
                                Intent in=new Intent(getApplicationContext(),payment.class);
                                in.putExtra("tot",res);
                                startActivity(in);



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
                        params.put("lid",sp.getString("lid",""));
                        params.put("bill_id",sp.getString("bid",""));
                        return params;

                    }
                };
                queue.add(stringRequest);


            }
        });

        RequestQueue queue = Volley.newRequestQueue(medicines.this);
        url ="http://"+sp.getString("ip", "") + ":5000/viewmedicines";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {

                    JSONArray ar=new JSONArray(response);
                    mid= new ArrayList<>();
                    mname= new ArrayList<>();
                    des= new ArrayList<>();
                    pic= new ArrayList<>();
                    price= new ArrayList<>();
                    exp= new ArrayList<>();
                    dos= new ArrayList<>();
                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);
                        mid.add(jo.getString("medicine_id"));
                        mname.add(jo.getString("medicine_name"));
                        des.add(jo.getString("discription"));
                        pic.add(jo.getString("photo"));
                        price.add(jo.getString("price"));
                        exp.add(jo.getString("exp_date"));
                        dos.add(jo.getString("dosage"));
                    }
                    // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                    //lv.setAdapter(ad);
                    lv1.setAdapter(new custom3(medicines.this,mname,des,price));
                    lv1.setOnItemClickListener(medicines.this);
                } catch (Exception e) {
                    Toast.makeText(medicines.this, "exp"+e, Toast.LENGTH_SHORT).show();
                    Log.d("=========", e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(medicines.this, "err"+error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("pid",sp.getString("pid",""));
                return params;
            }
        };
        queue.add(stringRequest);
    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent in=new Intent(getApplicationContext(),buymed.class);
        in.putExtra("mid",mid.get(i));
        in.putExtra("mname",mname.get(i));
        in.putExtra("des",des.get(i));
        in.putExtra("price",price.get(i));
        in.putExtra("pic",pic.get(i));
        in.putExtra("exp",exp.get(i));
        in.putExtra("dos",dos.get(i));
        startActivity(in);
    }
}