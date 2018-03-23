package com.nagizade.chatdemo;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.nagizade.chatdemo.Adapters.DatabaseAdapter;
import com.nagizade.chatdemo.Adapters.MessagesAdapter;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private String           username;
    private String           userNumber;
    private EditText         messageContent;
    private ImageButton      sendMessage;
    private DatabaseAdapter  helper;
    private List<MessageModel> messages = new ArrayList<>();
    private RecyclerView     messagesView;
    private MessagesAdapter  mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Intent intent = getIntent();
        username       = intent.getStringExtra("Contact name");
        userNumber     = intent.getStringExtra("Contact number");
        messageContent = (EditText) findViewById(R.id.messageContent);
        sendMessage    = (ImageButton) findViewById(R.id.sendMessage);
        messagesView = (RecyclerView) findViewById(R.id.chat_view);
        helper         = new DatabaseAdapter(this);


        SQLiteDatabase db = helper.getWritableDatabase();
        helper.createDb(db, userNumber);
        messages.addAll(helper.getAllMessages(userNumber));
        mAdapter = new MessagesAdapter(messages);
        RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(this);
        messagesView.setLayoutManager(mLayoutManager2);
        messagesView.setItemAnimator(new DefaultItemAnimator());
        // messagesView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
        messagesView.setAdapter(mAdapter);
        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessageClick(v);
            }
        });

    }

    public void sendMessageClick(View v) {
        String userN = "me";
        String messageText = messageContent.getText().toString();
        helper.insertMessage(userN, messageText, userNumber);
        messageContent.setText("");
        checker();

    }

    //Checking if new message was added and refreshing our RecyclerView
    public void checker() {
        if (helper.getMessagesCount(userNumber) > messages.size()) {
            messages.clear();
            messages.addAll(helper.getAllMessages(userNumber));
            mAdapter.notifyDataSetChanged();
        }
    }

}
