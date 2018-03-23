package com.nagizade.chatdemo.Adapters;

import android.app.Notification;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.nagizade.chatdemo.MessageModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hasan Nagizade on 3/23/2018.
 */
public class DatabaseAdapter extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "messages_db";


    public DatabaseAdapter(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    public void createDb(SQLiteDatabase db,String tableName) {
        // create table
        db.execSQL("CREATE TABLE IF NOT EXISTS " + MessageModel.TABLE_NAME+tableName + MessageModel.CREATE_TABLE);
    }

    public long insertMessage(String username,String messageContent,String tableName) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(MessageModel.MSG_USER, username);
        values.put(MessageModel.MSG_CONTENT, messageContent);

        // insert row
        long id = db.insert(MessageModel.TABLE_NAME+tableName, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public List<MessageModel> getAllMessages(String username) {
        List<MessageModel> messages = new ArrayList<>();
        String tableName = MessageModel.TABLE_NAME+username;

        // Select All Query
        String selectQuery = "SELECT  * FROM " + tableName + " ORDER BY " +
                MessageModel.MSG_TIME + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                MessageModel msgm = new MessageModel();
                msgm.setId(cursor.getInt(cursor.getColumnIndex(MessageModel.UID)));
                msgm.setUsername(cursor.getString(cursor.getColumnIndex(MessageModel.MSG_USER)));
                msgm.setTimestamp(cursor.getString(cursor.getColumnIndex(MessageModel.MSG_TIME)));
                msgm.setMessageContent(cursor.getString(cursor.getColumnIndex(MessageModel.MSG_CONTENT)));
                messages.add(msgm);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return messages list
        return messages;
    }

    public int getMessagesCount(String username) {
        String countQuery = "SELECT  * FROM " + MessageModel.TABLE_NAME+username;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + MessageModel.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }
}