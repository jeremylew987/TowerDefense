package com.se309.tower;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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

public class createLgoin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_lgoin);
        Button back = findViewById(R.id.button3);
        final TextView info = findViewById(R.id.textView3);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

            }});
        String address = "https://56be132c-7751-4deb-99d0-e96db2690a7c.mock.pstmn.io/createlogin";
        RequestQueue queue = Volley.newRequestQueue(createLgoin.this);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, address, null, new Response.Listener<JSONObject>() {
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
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(createLgoin.this);
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