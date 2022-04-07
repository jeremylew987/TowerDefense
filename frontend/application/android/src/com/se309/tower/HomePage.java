package com.se309.tower;

import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        Button logout = findViewById(R.id.logout);
        final SharedPreferences mPrefs = getSharedPreferences("test",0);
        String usernameSave = mPrefs.getString("username","none");
        String passwordSave = mPrefs.getString("password","none");
        /*
        if((usernameSave.equals("none") || passwordSave.equals("none"))) {
            startActivity(new Intent(HomePage.this, loginPage.class));
        }
*/
        Button curUser = findViewById(R.id.curUser);
        curUser.setText(usernameSave);


        Button joinGame = findViewById(R.id.JoinGame);
        Button createGame = findViewById(R.id.CreateGame);
        Button social = findViewById(R.id.Social);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                SharedPreferences.Editor mEditor = mPrefs.edit();
                mEditor.remove("username").commit();
                mEditor.remove("password").commit();
                startActivity(new Intent(HomePage.this, LoginPage.class));

            }});

        createGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomePage.this, LobbyPage.class));

            }});
        joinGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView code = findViewById(R.id.GameCode);

                SharedPreferences.Editor mEditor = mPrefs.edit();
                mEditor.putString("gameCode", code.getText().toString()).commit();
                startActivity(new Intent(HomePage.this, LobbyPage.class));

            }});
        social.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(HomePage.this, SocialPage.class));

            }});
        curUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(HomePage.this, ProfilePage.class));

            }});
    }
}