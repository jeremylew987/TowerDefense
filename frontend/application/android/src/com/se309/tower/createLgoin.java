package com.se309.tower;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
        final EditText username = findViewById(R.id.UsernameText);
        final EditText password = findViewById(R.id.Passowrdtext);
        final EditText email = findViewById(R.id.Email);
        Button submit = findViewById(R.id.CreateLogin);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

            }});
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = username.getText().toString();
                String pass = password.getText().toString();
                String emailS = email.getText().toString();


        String address = "https://56be132c-7751-4deb-99d0-e96db2690a7c.mock.pstmn.io/createlogin";
        RequestQueue queue = Volley.newRequestQueue(createLgoin.this);
                JSONObject data = new JSONObject();
                try {
                    data.put("username",name);
                    data.put("passphrase",pass);
                    data.put("email",emailS);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, address, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

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



        });

    }
}