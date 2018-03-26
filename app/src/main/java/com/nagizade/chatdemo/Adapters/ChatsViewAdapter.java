package com.nagizade.chatdemo.Adapters;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nagizade.chatdemo.Contact;
import com.nagizade.chatdemo.ItemClickListener;
import com.nagizade.chatdemo.LastMessageModel;
import com.nagizade.chatdemo.LastMessageModel;
import com.nagizade.chatdemo.R;

import java.io.ByteArrayInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Hasan Nagizade on 3/25/2018.
 */
public class ChatsViewAdapter extends RecyclerView.Adapter<ChatsViewAdapter.MyViewHolder> {

    private List<LastMessageModel> messagesList;
    private ItemClickListener mListener;


    public void setClickListener(ItemClickListener itemClickListener) {
        this.mListener = itemClickListener;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public   TextView          senderName,lastMessage,messageTime;
        public   ImageView         contactPic;
        private  ItemClickListener mListener;

        MyViewHolder(View itemView, ItemClickListener listener) {
            super(itemView);
            senderName = (TextView) itemView.findViewById(R.id.contactName);
            lastMessage = (TextView) itemView.findViewById(R.id.lastMessage);
            contactPic = (ImageView) itemView.findViewById(R.id.contactPicture);
            messageTime = (TextView) itemView.findViewById(R.id.messageTime);

            mListener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onItemClick(v, getAdapterPosition());
        }
    }

    public ChatsViewAdapter(List<LastMessageModel> messagesList,ItemClickListener listener) {
        this.messagesList = messagesList;
            mListener = listener;
    }
    @Override
    public ChatsViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_row, parent, false);

        return new MyViewHolder(itemView,mListener);
    }

    @Override
    public void onBindViewHolder(ChatsViewAdapter.MyViewHolder holder, int position) {
        LastMessageModel contact = messagesList.get(position);
        holder.senderName.setText(getContactName(contact.getProfilePic(),holder));
        holder.lastMessage.setText(contact.getLastMessage());
        holder.messageTime.setText(formatDate(contact.getTimestamp()));

        // If contact has a picture we set it as profile picture.
        // If variable u will be null then we don't have profile picture for this contact.
        // We will show default placeholder image
        Bitmap u = openPhoto(contact.getProfilePic(),holder);
        if (u != null) {
            holder.contactPic.setImageBitmap(u);
        } else {
            holder.contactPic.setImageResource(R.drawable.ic_person_black_48dp);
        }
    }

    @Override
    public int getItemCount() {
        return messagesList.size();
    }

    /**
     * Getting bitmap profile picture of contact
     */
    public Bitmap openPhoto(String contactId,ChatsViewAdapter.MyViewHolder holder) {
        Uri contactUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, new Long(contactId));
        Uri photoUri = Uri.withAppendedPath(contactUri, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);
        Cursor cursor = holder.itemView.getContext().getContentResolver().query(photoUri,
                new String[]{ContactsContract.Contacts.Photo.PHOTO}, null, null, null);
        if (cursor == null) {
            return null;
        }
        try {
            if (cursor.moveToFirst()) {
                byte[] data = cursor.getBlob(0);
                if (data != null) {
                    return BitmapFactory.decodeStream(new ByteArrayInputStream(data));
                }
            }
        } finally {
            cursor.close();
        }
        return null;

    }

    /**
     * Getting name of Contact
     */
    public String getContactName(String contactId,ChatsViewAdapter.MyViewHolder holder) {

        ContentResolver cr = holder.itemView.getContext().getContentResolver(); //Activity/Application android.content.Context
        String contactName="";

        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if(cursor.moveToFirst())
        {
            do
            {
                String id = contactId;

                if(Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0)
                {
                    Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",new String[]{ id }, null);
                    while (pCur.moveToNext())
                    {
                         contactName = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

                        break;

                    }
                    pCur.close();

                }

            } while (cursor.moveToNext()) ;
        }


        return contactName;
    }

    /**
     * Formatting timestamp to `HH:mm` format
     * Input: 2018-02-21 00:15:42
     * Output: 00:15
     */
    private String formatDate(String dateStr) {
        try {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = fmt.parse(dateStr);
            SimpleDateFormat fmtOut = new SimpleDateFormat("HH:mm");
            return fmtOut.format(date);
        } catch (ParseException e) {

        }

        return "";
    }
}