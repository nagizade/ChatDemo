package com.nagizade.chatdemo;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
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
    private String             contactID;
    private EditText           messageContent;
    private ImageButton        sendMessage;
    private DatabaseAdapter    helper;
    private List<MessageModel> messages = new ArrayList<>();
    private RecyclerView     messagesView;
    private MessagesAdapter  mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        //Showing back button on toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        username       = intent.getStringExtra("Contact name");
        userNumber     = intent.getStringExtra("Contact number").replaceAll("[\\D]", ""); //remove all special characters from phone number
        contactID      = intent.getStringExtra("Contact id");
        messageContent = (EditText) findViewById(R.id.messageContent);
        sendMessage    = (ImageButton) findViewById(R.id.sendMessage);
        messagesView = (RecyclerView) findViewById(R.id.chat_view);
        helper         = new DatabaseAdapter(this);

        getSupportActionBar().setTitle(username);
        SQLiteDatabase db = helper.getWritableDatabase();
        helper.createDb(db, userNumber);
        messages.addAll(helper.getAllMessages(userNumber));
        mAdapter = new MessagesAdapter(messages);
        RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,true);
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
        helper.insertLastMessage(userNumber,messageText,contactID);
        messageContent.setText("");
        checker();
        giveReply();

    }

    //Checking if new message was added and refreshing our RecyclerView
    public void checker() {
        if (helper.getMessagesCount(userNumber) > messages.size()) {
            messages.clear();
            messages.addAll(helper.getAllMessages(userNumber));
            mAdapter.notifyDataSetChanged();
        }
    }

    //Simple reply system from contact
    public void giveReply() {
        String userN = userNumber;
        String messageText = "Hello";
        helper.insertMessage(userN,messageText,userNumber);
        helper.insertLastMessage(userN,messageText,contactID);
        checker();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}