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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.nagizade.chatdemo.Adapters.DatabaseAdapter;
import com.nagizade.chatdemo.Adapters.MessagesAdapter;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private String             username;
    private String             userNumber;
    private String             contactID;
    private EditText           messageContent;
    private ImageButton        sendMessage;
    private DatabaseAdapter    helper;
    private List<MessageModel> messages = new ArrayList<>();
    private RecyclerView       messagesView;
    private MessagesAdapter    mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        //Showing back button on toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        username       = intent.getStringExtra("Contact name");
        userNumber     = intent.getStringExtra("Contact number");
        contactID      = intent.getStringExtra("Contact id");
        messageContent = (EditText) findViewById(R.id.messageContent);
        sendMessage    = (ImageButton) findViewById(R.id.sendMessage);
        messagesView = (RecyclerView) findViewById(R.id.chat_view);
        helper         = new DatabaseAdapter(this);


        if(userNumber.length() == 10) {
            userNumber = "994"+userNumber.substring(1); //remove the first zero and add 994 instead
        } else {
            userNumber = userNumber.replaceAll("[\\D]", ""); //remove all special characters from phone number
        }

        //To be sure that sent message is not empty we will disable and hide sendMessage button
        // It will be visible and enabled only when user enters something

        sendMessage.setEnabled(false);
        sendMessage.setVisibility(View.INVISIBLE);
        messageContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().equals("")) {
                    sendMessage.setEnabled(false);
                    sendMessage.setVisibility(View.INVISIBLE);
                } else {
                    sendMessage.setEnabled(true);
                    sendMessage.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //We will set username as title for acitivity
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

    //When sendMessage button is clicked we add entered message to the chat table with this user
    public void sendMessageClick(View v) {
        String messageText = messageContent.getText().toString().trim();
        String userN = "me";
        helper.insertMessage(userN, userNumber, userNumber,messageText);
        helper.insertLastMessage(username,userNumber,messageText,contactID);
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
        String messageText = "Hello";
        helper.insertMessage(username,userNumber,userNumber,messageText);
        helper.insertLastMessage(username,userNumber,messageText,contactID);
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