package com.se309.tower;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LeaderboardsPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboards_page);

        final Boolean[] FriendOnly = {false};
        final String[] mode = {"level"};
        Button back = findViewById(R.id.LeaderboardBack);
        setupLeaderList(mode[0],FriendOnly[0]);
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

                setupLeaderList(mode[0],FriendOnly[0]);
            }});

        Button Level = findViewById(R.id.LeaderboardLevel);
        Button Kills = findViewById(R.id.LeaderboardKills);
        Button Wins = findViewById(R.id.leaderboardsWins);
        Button Username = findViewById(R.id.leaderboardUsername);
        Level.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            mode[0] = "level";
        setupLeaderList("level",FriendOnly[0]);

            }});
        Kills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mode[0] = "kills";
                setupLeaderList("kills",FriendOnly[0]);

            }});
        Wins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mode[0] = "wins";
                setupLeaderList("wins",FriendOnly[0]);

            }});
        Username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mode[0] = "username";
                setupLeaderList("username",FriendOnly[0]);

            }});


    }

    void setupLeaderList(String mode, boolean friend){
        clearLayouts();
        final RequestQueue queue = Volley.newRequestQueue(LeaderboardsPage.this);
        String friendAdd = "";
        if(friend){
            friendAdd = "true";
        }
        else friendAdd = "false";

        //        String address = "http://coms-309-027.class.las.iastate.edu:8080/user/leaderboard?friendsOnly="+friendAdd+"&sortBy="+mode+"&order=descending";
        String address = "http://10.49.40.243:8080/user/leaderboard?friendsOnly="+friendAdd+"&sortBy="+mode+"&order=descending";
        JsonObjectRequest FriendList = new JsonObjectRequest(Request.Method.GET, address, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("Response: " , response.toString());
                JSONArray res = null;
                try {
                    res = response.getJSONArray("data");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < res.length(); i++) {
                    try {
                        Leaderboard(res.getJSONObject(i));
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

    }
    private void Leaderboard(JSONObject friend){
        ViewGroup layout = (ViewGroup) findViewById(R.id.LeaderboardUsernameList);
        Toolbar.LayoutParams lparams = new Toolbar.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.WRAP_CONTENT);
        TextView cur = new TextView(this);
        cur.setLayoutParams(lparams);
        try {
            cur.setText(friend.getString("username"));
        } catch (Exception e){
            e.printStackTrace();
        }
        layout.addView(cur);


        try{
            friend = friend.getJSONObject("stats");
        }catch (Exception e){
            e.printStackTrace();
        }


        layout = (ViewGroup) findViewById(R.id.leaderboardLevelList);
        cur = new TextView(this);
        cur.setLayoutParams(lparams);
        try {
            cur.setText(friend.getString("level"));
        } catch (Exception e){
            e.printStackTrace();
        }
        layout.addView(cur);

        layout = (ViewGroup) findViewById(R.id.leaderboardKillsList);
        cur = new TextView(this);
        cur.setLayoutParams(lparams);
        try {
            cur.setText(friend.getString("kills"));
        } catch (Exception e){
            e.printStackTrace();
        }
        layout.addView(cur);

        layout = (ViewGroup) findViewById(R.id.leaderboardsWinsList);
        cur = new TextView(this);
        cur.setLayoutParams(lparams);
        try {
            cur.setText(friend.getString("wins"));
        } catch (Exception e){
            e.printStackTrace();
        }
        layout.addView(cur);

        layout = (ViewGroup) findViewById(R.id.leaderboardsLossList);
        cur = new TextView(this);
        cur.setLayoutParams(lparams);
        try {
            cur.setText(friend.getString("losses"));
        } catch (Exception e){
            e.printStackTrace();
        }
        layout.addView(cur);


    }
    void clearLayouts(){
        ViewGroup layout = (ViewGroup) findViewById(R.id.LeaderboardUsernameList);
        layout.removeAllViews();
        TextView title = new TextView(this);
        title.setText("Username");
        layout.addView(title);

         layout = (ViewGroup) findViewById(R.id.leaderboardLevelList);
        layout.removeAllViews();
         title = new TextView(this);
        title.setText("Level");
        layout.addView(title);

        layout = (ViewGroup) findViewById(R.id.leaderboardKillsList);
        layout.removeAllViews();
        title = new TextView(this);
        title.setText("Kills");
        layout.addView(title);

        layout = (ViewGroup) findViewById(R.id.leaderboardsWinsList);
        layout.removeAllViews();
        title = new TextView(this);
        title.setText("Wins");
        layout.addView(title);

        layout = (ViewGroup) findViewById(R.id.leaderboardsLossList);
        layout.removeAllViews();
        title = new TextView(this);
        title.setText("Losses");
        layout.addView(title);

    }

}