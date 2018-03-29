package com.nagizade.chatdemo;

import android.graphics.Bitmap;

/**
 * Created by redShaman on 3/26/2018.
 */
public class LastMessageModel {

    public static final String LASTMSG_USER = "user_name";
    public static final String LASTMSG_USERNUMBER = "user_number";
    public static final String LASTMSG_TIME = "msg_time";
    public static final String LASTMSG_CONTENT = "messageContent";

    private int    id;
    private String msgDate;
    private String timestamp;
    private String username;
    private String usernumber;
    private String lastMessage;
    private String contact_id;



    // Create table SQL query
    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + "lastMessages" + "("
            + "_id" + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "user_name" + " TEXT ,"
            + "user_number" + " TEXT ,"
            + "contact_id" + " TEXT ,"
            + "msg_time" + " TIMESTAMP DATE DEFAULT (datetime('now','localtime')) ,"
            + "messageContent" + " TEXT"
            + ")";

    public LastMessageModel(int id, String timestamp,String username, String usernumber,String lastMessage, String contact_id) {

        this.id = id;
        this.timestamp = timestamp;
        this.username = username;
        this.usernumber = usernumber;
        this.lastMessage = lastMessage;
        this.contact_id = contact_id;

    }

    public LastMessageModel() {

    }

    public int getId() {
        return id;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setProfilePic(String contact_id) {
        this.contact_id = contact_id;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsernumber() {
        return usernumber;
    }

    public void setUsernumber(String usernumber) {
        this.usernumber = usernumber;
    }

    public String getProfilePic() {
        return contact_id;
    }

    public String getMsgDate() {
        return msgDate;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setId(int id) {
        this.id = id;
    }



    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
