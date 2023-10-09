package com.example.virtualdoc;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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

public class buymed extends AppCompatActivity {
    String mid,mname,des,price,dos,pic,exp,url;
    TextView t1,t2,t3,t4;
    EditText e1;
    Button b;
    SharedPreferences sh;
    ImageView i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buymed);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        t1=findViewById(R.id.textView48);
        t2=findViewById(R.id.textView50);
        t3=findViewById(R.id.textView52);
        t4=findViewById(R.id.textView55);
        e1=findViewById(R.id.editTextTextPersonName15);
        b=findViewById(R.id.button20);
        i=findViewById(R.id.imageView);
        mid=getIntent().getStringExtra("mid");
        mname=getIntent().getStringExtra("mname");
        des=getIntent().getStringExtra("des");
        price=getIntent().getStringExtra("price");
        exp=getIntent().getStringExtra("exp");
        pic=getIntent().getStringExtra("pic");
        dos=getIntent().getStringExtra("dos");
        t1.setText(mname);
        t2.setText(des);
        t3.setText(price);
        t4.setText(exp);
        if(android.os.Build.VERSION.SDK_INT>9)
        {
            StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        String urll="http://"+sh.getString("ip","")+":5000/static/medicine/"+pic;
        java.net.URL thumb_u;
        try {
            thumb_u = new java.net.URL(urll);
            Drawable thumb_d = Drawable.createFromStream(thumb_u.openStream(), "src");
            i.setImageDrawable(thumb_d);
        }
        catch(Exception e){
            Log.d("*********",e.toString());
        }

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String qty=e1.getText().toString();
                if(qty.equalsIgnoreCase(""))
                {
                    e1.setError("ENTER QUANTITY");
                }
                else {
                    RequestQueue queue = Volley.newRequestQueue(buymed.this);
                    url = "http://" + sh.getString("ip", "") + ":5000/bookmed";

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
                                    Toast.makeText(buymed.this, "Success", Toast.LENGTH_SHORT).show();
                                    Intent ik = new Intent(getApplicationContext(), medicines.class);
                                    startActivity(ik);
                                } else {
                                    Toast.makeText(buymed.this, "order Failed", Toast.LENGTH_SHORT).show();
                                    Intent ik = new Intent(getApplicationContext(), HOME.class);
                                    startActivity(ik);
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
                            params.put("lid", sh.getString("lid", ""));
                            params.put("mid", mid);
                            params.put("qty", qty);
                            params.put("bill_id", sh.getString("bid", ""));
                            return params;
                        }
                    };
                    queue.add(stringRequest);
                }
            }
        });
    }
}