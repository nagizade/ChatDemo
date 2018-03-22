package com.nagizade.chatdemo.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nagizade.chatdemo.AppTabs.ContactsFragment;
import com.nagizade.chatdemo.Contact;
import com.nagizade.chatdemo.ItemClickListener;
import com.nagizade.chatdemo.R;

import java.util.List;

/**
 * Created by Hasan Nagizade on 3/21/2018.
 */
public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.MyViewHolder> {

    private List<Contact> contactList;
    private ItemClickListener mListener;

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mListener = itemClickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView contactName,contactNumber;
        private ItemClickListener mListener;

        MyViewHolder(View itemView, ItemClickListener listener) {
            super(itemView);
            contactName = (TextView) itemView.findViewById(R.id.contactName);
            contactNumber = (TextView) itemView.findViewById(R.id.contactNumber);
            mListener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
           mListener.onItemClick(v, getAdapterPosition());
        }
    }

    public ContactsAdapter(List<Contact> contactList,ItemClickListener listener) {
        this.contactList = contactList;
             mListener = listener;
    }
    @Override
    public ContactsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_row,parent,false);

        return new MyViewHolder(itemView,mListener);
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