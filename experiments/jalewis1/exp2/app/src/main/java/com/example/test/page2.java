package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class page2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page2);
        Button back = findViewById(R.id.button3);
        TextView info = findViewById(R.id.textView3);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

            }});
String address = "https://35dbb763-3eb6-4fea-8b30-f5cd4fdd1e3a.mock.pstmn.io/test";
        RequestQueue queue = Volley.newRequestQueue(page2.this);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, address, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                     info.setText(response.getString("response"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(page2.this);
                alertDialogBuilder.setTitle("Error");
                alertDialogBuilder.setMessage(error.getMessage());
                alertDialogBuilder.setPositiveButton("Ok", null);
                alertDialogBuilder.setNegativeButton("", null);
                alertDialogBuilder.create().show();
            }
        });
        queue.add(request);


    }
}