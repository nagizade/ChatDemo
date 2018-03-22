package com.nagizade.chatdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        //Testing if correct data is coming

        Intent intent = getIntent();
        TextView text1 = (TextView) findViewById(R.id.textView);
        TextView text2 = (TextView) findViewById(R.id.textView2);

        text1.setText("Hi!. Selected Contact is:" + " " + intent.getStringExtra("Contact name"));
        text2.setText("Phone number is:" + " " + intent.getStringExtra("Contact number"));
    }
}
