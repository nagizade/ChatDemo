package com.nagizade.chatdemo;


import android.graphics.Bitmap;
import android.net.Uri;

/**
 * Created by Hasan Nagizade on 3/21/2018.
 */
public class Contact {
    private String contact_name,contact_number;
    private Bitmap profilePic;


    public Contact () {

    }
    public Contact(String contact_name , String contact_number, Bitmap profilePic) {
        this.contact_name = contact_name;
        this.contact_number = contact_number;
        this.profilePic = profilePic;
    }

    public String getContact_name() {
        return contact_name;
    }

    public void setContact_name(String contact_name) {
        this.contact_name = contact_name;
    }

    public String getContact_number () {
        return contact_number;
    }

    public void setContact_number(String contact_number) {
        this.contact_number = contact_number;
    }

    public Bitmap getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(Bitmap profilePic) {
        this.profilePic = profilePic;
    }
}
