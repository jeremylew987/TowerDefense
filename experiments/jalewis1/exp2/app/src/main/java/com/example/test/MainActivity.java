package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        EditText username = findViewById(R.id.editTextTextPersonName);
        EditText password = findViewById(R.id.editTextTextPassword);
        TextView username1 = findViewById(R.id.textView);
        TextView password1 = findViewById(R.id.textView2);
        Button submit = findViewById(R.id.button);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = username.getText().toString();
                String pass = password.getText().toString();
                username1.setText(name);
                password1.setText(pass);


                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                JSONObject data = new JSONObject();
                try {
                    data.put("username",name);
                    data.put("passphrase",pass);

                } catch (JSONException e) {
                    e.printStackTrace();
                }




            }



            });



    }
}