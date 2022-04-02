package com.se309.tower;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProfilePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);


        TextView Details = findViewById(R.id.ProfileDetails);
        Details.setText(GetProfileDetails());

        Button back = findViewById(R.id.ProfileBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

            }});

    }

    String GetProfileDetails(){
        final RequestQueue queue = Volley.newRequestQueue(ProfilePage.this);
        final String[] result = {""};
        String friendaddress =  "http://coms-309-027.class.las.iastate.edu:8080/user";
        JsonObjectRequest FriendList = new JsonObjectRequest(Request.Method.GET, friendaddress, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("Response: " , response.toString());
                try {
                    result[0] +="Username: " + response.getString("username") + "\n";
                    result[0] +="Email: " + response.getString("email") + "\n";
                    result[0] +="Role: " + response.getString("role") + "\n";

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(FriendList);
        return result[0];
    }



}