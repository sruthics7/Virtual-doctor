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
import android.widget.EditText;
import android.widget.Spinner;
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

public class paymentdoc extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    SharedPreferences sp;
    String url="",ip="",bk;
    String [] bnk={"SBI","FEDERAL BANK"};
    Spinner s1;
    EditText e1,e2;
    TextView t1;
    Button b1;
    String amt="200";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paymentdoc);
        s1=findViewById(R.id.spinner3);
        t1=findViewById(R.id.textView46);
        e1=findViewById(R.id.editTextTextPersonName13);
        e2=findViewById(R.id.editTextTextPersonName14);
        b1=findViewById(R.id.button19);
        t1.setText(amt);

        sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        ArrayAdapter<String> ad=new ArrayAdapter<String>(paymentdoc.this,android.R.layout.simple_list_item_1,bnk);
        s1.setAdapter(ad);
        s1.setOnItemSelectedListener(paymentdoc.this);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ifsc=e1.getText().toString();
                String accno=e2.getText().toString();
                bk=s1.getSelectedItem().toString();
                if(ifsc.equalsIgnoreCase(""))
                {
                    e1.setError("ENTER IFSC CODE");
                }
                else if(accno.equalsIgnoreCase(""))
                {
                    e2.setError("ENTER ACCOUNT NO");
                }
                else {
                    RequestQueue queue = Volley.newRequestQueue(paymentdoc.this);
                    url = "http://" + sp.getString("ip", "") + ":5000/bookdoc";

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


                                    Toast.makeText(paymentdoc.this, "Booking Success", Toast.LENGTH_SHORT).show();
                                    Intent ik = new Intent(getApplicationContext(), HOME.class);
                                    startActivity(ik);


                                } else {

                                    Toast.makeText(paymentdoc.this, "Booking Failed", Toast.LENGTH_SHORT).show();
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
                            params.put("sid", getIntent().getStringExtra("sid"));
                            params.put("lid", sp.getString("lid", ""));
                            params.put("acc", accno);
                            params.put("ifsc", ifsc);
                            params.put("amt", amt);
                            params.put("bnk", bk);
                            return params;

                        }
                    };
                    queue.add(stringRequest);
                }
            }
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//       bk=bnk[i];
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}