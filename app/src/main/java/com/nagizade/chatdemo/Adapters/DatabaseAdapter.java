package com.nagizade.chatdemo.Adapters;

/**
 * Created by Hasan Nagizade on 3/22/2018.
 */
    import android.content.ContentValues;
    import android.content.Context;
    import android.database.Cursor;
    import android.database.sqlite.SQLiteDatabase;
    import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseAdapter {
    myDbHelper myhelper;
    public DatabaseAdapter(Context context)
    {
        myhelper = new myDbHelper(context);
    }

    public long insertData(String user, String message)
    {
        SQLiteDatabase dbb = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.MSG_USER, user);
        contentValues.put(myDbHelper.MSG_CONTENT, message);
        long id = dbb.insert(myDbHelper.TABLE_NAME, null , contentValues);
        return id;
    }

    public String getData()
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myDbHelper.UID,myDbHelper.MSG_DATE,myDbHelper.MSG_TIME,myDbHelper.MSG_USER,myDbHelper.MSG_CONTENT};
        Cursor cursor =db.query(myDbHelper.TABLE_NAME,columns,null,null,null,null,null);
        StringBuffer buffer= new StringBuffer();
        while (cursor.moveToNext())
        {
            int cid =cursor.getInt(cursor.getColumnIndex(myDbHelper.UID));
            String  messageDate    =cursor.getString(cursor.getColumnIndex(myDbHelper.MSG_DATE));
            String  messageTime    =cursor.getString(cursor.getColumnIndex(myDbHelper.MSG_TIME));
            String  messageSender  =cursor.getString(cursor.getColumnIndex(myDbHelper.MSG_USER));
            String  messageContent =cursor.getString(cursor.getColumnIndex(myDbHelper.MSG_CONTENT));
            buffer.append(cid+ "   " + messageDate + "   " + messageTime + "   " + messageSender + "   " + messageContent + " \n");
        }
        return buffer.toString();
    }

    public  int delete(String uname)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] whereArgs ={uname};

        int count =db.delete(myDbHelper.TABLE_NAME ,myDbHelper.MSG_DATE+" = ?",whereArgs);
        return  count;
    }

  /*  public int updateName(String oldName , String newName)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.M,newName);
        String[] whereArgs= {oldName};
        int count =db.update(myDbHelper.TABLE_NAME,contentValues, myDbHelper.TITLE+" = ?",whereArgs );
        return count;
    }*/

    static class myDbHelper extends SQLiteOpenHelper
    {
        private static final String DATABASE_NAME = "Chats";    // Database Name
        private static final String TABLE_NAME = "ChatWith";   // Table Name
        private static final int DATABASE_Version = 1;   // Database Version
        private static final String UID="_id";     // Column I (Primary Key)
        private static final String MSG_DATE = "Date";    //Column II
        private static final String MSG_TIME= "Time";    // Column III
        private static final String MSG_USER="User";
        private static final String MSG_CONTENT="Message";
        private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+
                " ("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+MSG_DATE+" DATE() ,"+ MSG_TIME+" TIME() ,"+MSG_USER+" TEXT ,"+MSG_CONTENT+" TEXT";
        private static final String DROP_TABLE ="DROP TABLE IF EXISTS "+TABLE_NAME;
        private Context context;

        public myDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_Version);
            this.context=context;
        }

        public void onCreate(SQLiteDatabase db) {

            try {
                db.execSQL(CREATE_TABLE);
            } catch (Exception e) {

            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {

                db.execSQL(DROP_TABLE);
                onCreate(db);
            }catch (Exception e) {

            }
        }
    }
}
