package com.se309.tower;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SocialPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social);
        final RequestQueue queue = Volley.newRequestQueue(SocialPage.this);
        final SharedPreferences mPrefs = getSharedPreferences("test",0);


        Button back = findViewById(R.id.backSocial);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

            }});


        Button addFriend = findViewById(R.id.addFriendButton);
        final EditText addFriendText = findViewById(R.id.addFriendText);

        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String friendUsername = addFriendText.getText().toString();
                String usernameSave = mPrefs.getString("username","none");
                String friendrequestaddress =  "http://coms-309-027.class.las.iastate.edu:8080/friends/" + usernameSave + "/"+friendUsername;
                //call add friend command
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, friendrequestaddress, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String res = "";
                        try {
                            res = response.getString("response");
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SocialPage.this);
                            alertDialogBuilder.setTitle("response");
                            alertDialogBuilder.setMessage(res);
                            alertDialogBuilder.setPositiveButton("Ok", null);
                            alertDialogBuilder.setNegativeButton("", null);
                            alertDialogBuilder.create().show();
                        } catch (JSONException e) {
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SocialPage.this);
                            alertDialogBuilder.setTitle("Success");
                            alertDialogBuilder.setMessage("Check your email");
                            alertDialogBuilder.setPositiveButton("Ok", null);
                            alertDialogBuilder.setNegativeButton("", null);
                            alertDialogBuilder.create().show();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        error.printStackTrace();
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SocialPage.this);
                        alertDialogBuilder.setTitle("Error");
                        alertDialogBuilder.setMessage(error.getMessage());
                        alertDialogBuilder.setPositiveButton("Ok", null);
                        alertDialogBuilder.setNegativeButton("", null);
                        alertDialogBuilder.create().show();
                    }
                });
                queue.add(request);
            }});

        //call for friends TODO loop through using friend function


        String usernameSave = mPrefs.getString("username","none");
        String friendaddress =  "http://coms-309-027.class.las.iastate.edu:8080/friends/" + usernameSave;
        JsonArrayRequest FriendList = new JsonArrayRequest(Request.Method.GET, friendaddress, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.i("Response: " , response.toString());
                for (int i = 0; i < response.length(); i++) {
                    try {
                        friend(response.getJSONObject(i));
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(FriendList);
        //same for freiendRquests



    }


    private void friend(JSONObject friend){
        ViewGroup layout = (ViewGroup) findViewById(R.id.friendList);
        Toolbar.LayoutParams lparams = new Toolbar.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.WRAP_CONTENT);

        TextView cur = new TextView(this);
        cur.setLayoutParams(lparams);
        try {
            cur.setText(friend.getString("username"));
        } catch (Exception e){
            e.printStackTrace();
        }

        layout.addView(cur);

    }

    private void friendRequest(JSONObject friend){
        ViewGroup layout = (ViewGroup) findViewById(R.id.friendRequest);
        Toolbar.LayoutParams lparams = new Toolbar.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.WRAP_CONTENT);

        TextView cur = new TextView(this);
        cur.setLayoutParams(lparams);
        try {
            cur.setText(friend.getString("username"));
        } catch (Exception e){
            e.printStackTrace();
        }

        layout.addView(cur);

    }
}