package com.se309.tower;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toolbar;

import org.json.JSONObject;

public class SocialPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social);


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
                //call add friend command

            }});

        //call for friends TODO loop through using friend function
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