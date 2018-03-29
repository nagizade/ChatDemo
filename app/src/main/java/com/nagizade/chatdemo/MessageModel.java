package com.nagizade.chatdemo;

/**
 * Created by Hasan Nagizade on 3/23/2018.
 */
public class MessageModel{



    public static final String TABLE_NAME = "ChatWith";   // Table Name
    public static final String UID="_id";     // Column I (Primary Key)
    public static final String MSG_DATE = "Date";    //Column II
    public static final String MSG_TIME= "Time";    // Column III
    public static final String MSG_USER="User";
    public static final String USER_NUMBER="UserNumber";
    public static final String MSG_CONTENT="Message";
    public static final String DROP_TABLE ="DROP TABLE IF EXISTS "+TABLE_NAME;


    private int    id;
    private String msgDate;
    private String timestamp;
    private String username;
    private String phoneNumber;
    private String messageContent;



    // Create table SQL query
    public static final String CREATE_TABLE = " ("
            + UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
            + MSG_TIME+" TIMESTAMP DATE DEFAULT (datetime('now','localtime')),"
            + MSG_USER+" TEXT ,"
            + USER_NUMBER+" TEXT ,"
            + MSG_CONTENT+" TEXT"
            + ")";


    public MessageModel() {
    }

    public MessageModel(int id, String timestamp,String username,String phoneNumber,String messageContent) {
        this.id = id;
        this.timestamp = timestamp;
        this.username = username;
        this.messageContent = messageContent;
        this.phoneNumber = phoneNumber;
    }


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }


}