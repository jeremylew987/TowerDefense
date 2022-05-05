package com.se309.tower;

import android.app.AlertDialog;
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

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.regex.Pattern;

public class CreateLoginPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_lgoin);
        Button back = findViewById(R.id.button3);
        final EditText username = findViewById(R.id.UsernameText);
        final EditText password = findViewById(R.id.Passowrdtext);
        final EditText email = findViewById(R.id.Email);
        Button submit = findViewById(R.id.CreateLogin);
        final SharedPreferences mPrefs = getSharedPreferences("test",0);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

            }});
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = username.getText().toString();
                final String pass = password.getText().toString();


                String emailS = email.getText().toString();

                if(!isValidEmail(emailS)) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CreateLoginPage.this);
                    alertDialogBuilder.setTitle("Error");
                    alertDialogBuilder.setMessage("Invalid Email");
                    alertDialogBuilder.setPositiveButton("Ok", null);
                    alertDialogBuilder.setNegativeButton("", null);
                    alertDialogBuilder.create().show();
                    return;
                }





        //String address = "https://56be132c-7751-4deb-99d0-e96db2690a7c.mock.pstmn.io/createlogin";
        // registration http://localhost:8080/registration
                //String address = "http://10.48.40.205:8080/registration/";
                String address =  "http://coms-309-027.class.las.iastate.edu:8080/registration/";
                /**
                 * if submit pressed it goes through the login process of submiting it to the backend with androidvolley
                 */
        RequestQueue queue = Volley.newRequestQueue(CreateLoginPage.this);
                JSONObject data = new JSONObject();
                try {
                    data.put("username",name);

                    data.put("email",emailS);

                    data.put("password",pass);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, address, data, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String res = "";
                try {

                    response = response.getJSONObject("data");
                    res = response.getString("token");
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CreateLoginPage.this);
                    alertDialogBuilder.setTitle("Check Email");
                    alertDialogBuilder.setMessage("Register token: "+res);
                    alertDialogBuilder.setPositiveButton("Ok", null);
                    alertDialogBuilder.setNegativeButton("", null);
                    alertDialogBuilder.create().show();
                    TextView token = findViewById(R.id.tokenText);
                    token.setText(res);

                } catch (JSONException e) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CreateLoginPage.this);
                    alertDialogBuilder.setTitle("error");
                    alertDialogBuilder.setMessage("Issue completing the request");
                    alertDialogBuilder.setPositiveButton("Ok", null);
                    alertDialogBuilder.setNegativeButton("", null);
                    alertDialogBuilder.create().show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CreateLoginPage.this);
                alertDialogBuilder.setTitle("Error");
                alertDialogBuilder.setMessage("Issue completing the request \nusername or email may be already be in use");
                alertDialogBuilder.setPositiveButton("Ok", null);
                alertDialogBuilder.setNegativeButton("", null);
                alertDialogBuilder.create().show();
            }
        });
        queue.add(request);
            }



        });

    }

    /**
     * Validates that it is in email format
     * @param email
     * @return if it fits the format
     */
    public static boolean isValidEmail(String email){

        return Pattern.compile("^(.+)@(\\S+)$").matcher(email).matches();
    }

}