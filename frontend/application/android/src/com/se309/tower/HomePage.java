package com.se309.tower;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Button back = findViewById(R.id.Back2);
        Button logout = findViewById(R.id.logout);
        final SharedPreferences mPrefs = getSharedPreferences("test",0);
        String usernameSave = mPrefs.getString("username","none");
        String passwordSave = mPrefs.getString("password","none");
        if((usernameSave.equals("none") || passwordSave.equals("none"))) {
            startActivity(new Intent(HomePage.this, loginPage.class));
        }

        TextView curUser = findViewById(R.id.curUser);
        curUser.setText(usernameSave);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

            }});
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                SharedPreferences.Editor mEditor = mPrefs.edit();
                mEditor.remove("username").commit();
                mEditor.remove("password").commit();
                startActivity(new Intent(HomePage.this, loginPage.class));

            }});

    }
}