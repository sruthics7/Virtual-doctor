package com.example.virtualdoc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
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

public class searchdr extends AppCompatActivity  {
    Spinner s1;
    SharedPreferences sp;
    String url="",ip="",sym,res="",res1;
    Button b1,b2;
    TextView t1;
    ListView l1;
    ArrayList<String> sitem=new ArrayList<String>();

    String [] symptoms={"fever", "cough", "bodypain", "weakness", "diabetic", "decreased foot intake",
            "Abdominal pain", "gas truble", "vomiting", "constipation", "loose stool,stomach upset",
            "chest pain", "pedal edema associated with breathing difficulty", "chest tightness", "pain in the neck", "reduced ability to exercise", "swelling",
            "urinary incontinance", "urinary urgency", "lower abdominal pain", "swelling present on the prostate", "urinary infection", "foul-smelling urine",
            "appendicitis", "hernia", "abdominal pain", "rectal bleeding", "severe abdominal cramping", "bloating",
            "severe head ache", "weakness", "numbness", "slurring of speech", "facial deviation", "epilepsy",
            "breathing difficulty", "cough", "laboured breathing", "snoring", "coughing up blood", "lingering chest pain",
            "edema", "breathing difficulty", "decreased urine out put", "increased creatinine","urea level", "frothy urine",
            "uncontrolled diabetic", "hyperthyroidism","hyperthyroidism", "hyponatremia", "electrolyte imbalance", "depression",
            "earpain", "throat pain", "swallowing difficulty", "hearing loss", "nose bleeding"," ear infections", "head ache",
            "vision loss", "Congectivitis"," watering of eyes", "redness of eyes", "double vision", "itching of eyes",
            "fracture", "knee pain", "joint pain", "tumours", "swelling", "muscle spasms"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchdr);
        s1=findViewById(R.id.spinner2);
        b1=findViewById(R.id.button15);
        b2=findViewById(R.id.button16);
        l1=findViewById(R.id.lv1);
        sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        ArrayAdapter<String> ad=new ArrayAdapter<>(searchdr.this,android.R.layout.simple_list_item_1,symptoms);
        s1.setAdapter(ad);
//        s1.setOnItemS
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sym=s1.getSelectedItem().toString();
                res+=sym+"#";
                sitem.add(sym);
                try {
//                    JSONArray ar=new JSONArray(res);
//
//
//                for(int i=0;i<ar.length();i++)
//                {
//                    JSONObject jo=ar.getJSONObject(0);
//                    res1=jo.getString(ar+"");
//
//                }

                 ArrayAdapter<String> ad1=new ArrayAdapter<String>(searchdr.this,android.R.layout.simple_list_item_1,sitem);
                l1.setAdapter(ad1);
                } catch (Exception e) {
                    Toast.makeText(searchdr.this, "exp"+e, Toast.LENGTH_SHORT).show();

                    Log.d("=========", e.toString());
                }
            }

        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent i=new Intent(getApplicationContext(),viewdr.class);
                    i.putExtra("sl",res);
                    startActivity(i);
                Toast.makeText(getApplicationContext(),res,Toast.LENGTH_LONG).show();
            }
        });




    }
}