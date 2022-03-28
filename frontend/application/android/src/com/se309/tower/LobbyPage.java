package com.se309.tower;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LobbyPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);
        Button back = findViewById(R.id.lobbyBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final SharedPreferences mPrefs = getSharedPreferences("test",0);
                SharedPreferences.Editor mEditor = mPrefs.edit();
                mEditor.remove("gameCode").commit();

                finish();

            }});
    }
}