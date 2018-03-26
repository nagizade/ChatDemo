package com.nagizade.chatdemo;


import android.graphics.Bitmap;
import android.net.Uri;

/**
 * Created by Hasan Nagizade on 3/21/2018.
 */
public class Contact {
    private String contact_name,contact_number,contactID;
    private Bitmap profilePic;


    public Contact(String contactName, String contactNumber, Bitmap contactPic) {

    }
    public Contact(String contact_name , String contact_number, Bitmap profilePic,String contactID) {
        this.contact_name = contact_name;
        this.contact_number = contact_number;
        this.profilePic = profilePic;
        this.contactID = contactID;
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

    public String getContactID() {
        return contactID;
    }

    public void setContactID(String contactID) {
        this.contactID = contactID;
    }
}
