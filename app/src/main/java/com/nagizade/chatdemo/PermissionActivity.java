package com.nagizade.chatdemo;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.nagizade.chatdemo.Adapters.DatabaseAdapter;

public class PermissionActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 1;
    SQLiteDatabase myDb;
    DatabaseAdapter myHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Checking if we already have READ_CONTACTS permission. If we have it then we start
        // MainActivity and finish this activity. If we don't have READ_CONTACTS permission
        // we set click listener for askPermission button in this click listener we request
        // READ_CONTACTS permission.

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                == PackageManager.PERMISSION_GRANTED) {
            Intent i = new Intent(this,MainActivity.class);
            startActivity(i);
              finish();
            } else {
            setContentView(R.layout.activity_permission);

            Button askPermission = (Button) findViewById(R.id.askPermission);

            askPermission.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                      requestMultiplePermissions();
                }
        });
        }
    }

    //If permission granted then we run checkFirstRun function then start MainActivity and finish this activity

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE && grantResults.length == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                checkFirstRun();
                Intent i = new Intent(this,MainActivity.class);
                startActivity(i);
                finish();
            }

    //If permission is denied we finish activity
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                finish();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void requestMultiplePermissions() {
        ActivityCompat.requestPermissions(this,
                new String[]{
                        Manifest.permission.READ_CONTACTS
                },
                PERMISSION_REQUEST_CODE);
    }

    //Checking if app runs for the first time and creating lastMessages table to avoid
    //problems in future

    private void checkFirstRun() {

        final String PREFS_NAME = "MyPrefsFile";
        final String PREF_VERSION_CODE_KEY = "version_code";
        final int DOESNT_EXIST = -1;


        // Get current version code
        int currentVersionCode = BuildConfig.VERSION_CODE;

        // Get saved version code
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int savedVersionCode = prefs.getInt(PREF_VERSION_CODE_KEY, DOESNT_EXIST);

        // Check for first run or upgrade
        if (currentVersionCode == savedVersionCode) {

            // This is just a normal run
            return;

        } else if (savedVersionCode == DOESNT_EXIST) {

            // This is a new install (or the user cleared the shared preferences)

            myHelper = new DatabaseAdapter(this);
            myDb = myHelper.getWritableDatabase();
            myHelper.createLastMessagesTable(myDb);
            myDb.close();
        } else if (currentVersionCode > savedVersionCode) {

            // TODO This is an upgrade
        }

        // Update the shared preferences with the current version code
        prefs.edit().putInt(PREF_VERSION_CODE_KEY, currentVersionCode).apply();

    }
}
