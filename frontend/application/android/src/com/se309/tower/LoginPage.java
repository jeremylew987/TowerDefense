package com.se309.tower;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
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
import com.se309.config.NetworkConfig;
import com.se309.game.GameLauncher;
import com.se309.net.NetworkManager;
import com.se309.test.NetworkManagerTestBench;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.CookieHandler;
import java.net.CookieManager;

public class LoginPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        final EditText username = findViewById(R.id.editTextTextPersonName);
        final EditText password = findViewById(R.id.editTextTextPassword);
        final TextView username1 = findViewById(R.id.textView);
        final TextView password1 = findViewById(R.id.textView2);
        Button submit = findViewById(R.id.button);

        CookieManager cookieManager = new CookieManager();
        CookieHandler.setDefault(cookieManager);

        // Set up initial network manager
        final NetworkManager networkManager = new NetworkManager(this, NetworkConfig.BACKEND_URL);

        // Get reference for debug button
        Button debug = findViewById(R.id.debugButton);



        // Create Click Listener for debug routine
        debug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println("Starting network tests...");

                NetworkManagerTestBench.testNetworkFunctions(networkManager, LoginPage.this);

                Intent intent = new Intent(getBaseContext(), GameLauncher.class);
                startActivity(intent);

            }
        });

        final SharedPreferences mPrefs = getSharedPreferences("test",MODE_PRIVATE);
        String usernameSave = mPrefs.getString("username","none");
        String passwordSave = mPrefs.getString("password","none");
        /**
         * checks if stored user, if so tries to log in, if failed returns reason
         */
        if(!(usernameSave.equals("none") || passwordSave.equals("none"))) {
            username1.setText(usernameSave);
            password1.setText(passwordSave);
            RequestQueue queue = Volley.newRequestQueue(LoginPage.this);
            JSONObject data = new JSONObject();
            try {
                data.put("username",usernameSave);
                data.put("password",passwordSave);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            String address =  "http://coms-309-027.class.las.iastate.edu:8080/login";
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, address, data, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    String res = "";
                    try {
                        res = response.getString("message");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if(res.equals("Login Success")) {
                        password1.setText(res);
                        startActivity(new Intent(LoginPage.this, HomePage.class));
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    startActivity(new Intent(LoginPage.this, HomePage.class));
                    error.printStackTrace();
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoginPage.this);
                    alertDialogBuilder.setTitle("Error");
                    alertDialogBuilder.setMessage(error.getMessage());
                    alertDialogBuilder.setPositiveButton("Ok", null);
                    alertDialogBuilder.setNegativeButton("", null);
                    alertDialogBuilder.create().show();
                }
            });
            queue.add(request);
        }





        Button page = findViewById(R.id.button2);
        /**
         * goes to create login page if button is pressed
         */
        page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginPage.this, CreateLoginPage.class));
            }});
        /**
         * if submit pressed it goes through the login process of submiting it to the backend with androidvolley
         */
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = username.getText().toString();
                final String pass = password.getText().toString();
                username1.setText(name);
                password1.setText(pass);


                RequestQueue queue = Volley.newRequestQueue(LoginPage.this);
                JSONObject data = new JSONObject();
                try {
                    data.put("username",name);
                    data.put("password",pass);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String address =  "http://coms-309-027.class.las.iastate.edu:8080/login";
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, address, data, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String res = "";
                        String token = "";
                        try {
                            res = response.getString("message");
                            token = response.getString("confirmationToken");

                        } catch (JSONException e) {
                            e.printStackTrace();
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoginPage.this);
                            alertDialogBuilder.setTitle("Error");
                            alertDialogBuilder.setMessage("Bad USER OR PASS");
                            alertDialogBuilder.setPositiveButton("Ok", null);
                            alertDialogBuilder.setNegativeButton("", null);
                            alertDialogBuilder.create().show();
                        }
                        if(res.equals("Login Success")) {
                            SharedPreferences.Editor mEditor = mPrefs.edit();
                            mEditor.putString("username", name).commit();
                            if(!token.equals(null))
                            mEditor.putString("token", token).commit();
                            mEditor.putString("password", pass).commit();
                            password1.setText(res);
                            username1.setText(mPrefs.getString("username","none"));
                            password1.setText(mPrefs.getString("password","none"));
                            password1.setText(res);
                            startActivity(new Intent(LoginPage.this, HomePage.class));
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        error.printStackTrace();
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoginPage.this);
                        alertDialogBuilder.setTitle("Error");
                        alertDialogBuilder.setMessage("Bad username or Password");
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