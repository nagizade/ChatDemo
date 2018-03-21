package com.nagizade.chatdemo.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nagizade.chatdemo.Contact;
import com.nagizade.chatdemo.R;

import java.util.List;

/**
 * Created by Hasan Nagizade on 3/21/2018.
 */
public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.MyViewHolder> {

    private List<Contact> contactList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView contactName,contactNumber;

        public MyViewHolder(View view) {
            super(view);

            contactName = (TextView) view.findViewById(R.id.contactName);
            contactNumber = (TextView) view.findViewById(R.id.contactNumber);
        }
    }

    public ContactsAdapter(List<Contact> contactList) {
        this.contactList = contactList;
    }
    @Override
    public ContactsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_row,parent,false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ContactsAdapter.MyViewHolder holder, int position) {
          Contact contact = contactList.get(position);
          holder.contactName.setText(contact.getContact_name());
          holder.contactNumber.setText(String.valueOf(contact.getContact_number()));
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }
}
