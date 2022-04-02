package com.se309.tower;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LeaderboardsPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboards_page);

        final Boolean[] FriendOnly = {false};

        Button back = findViewById(R.id.LeaderboardBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

            }});
        final Button Friend = findViewById(R.id.LeaderboardFriends);
        Friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if(FriendOnly[0]){
                FriendOnly[0] = false;
                Friend.setText("Friends only: OFF");

            }
            else {
                FriendOnly[0] = true;
                Friend.setText("Friends only: ON");
            }


            }});

        Button Level = findViewById(R.id.LeaderboardLevel);
        Button Round = findViewById(R.id.LeaderboardRound);
        Level.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

setupLeaderList("Level");

            }});
        Round.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setupLeaderList("Round");

            }});


    }

    void setupLeaderList(String mode){

        String address = "";
        
    }
}