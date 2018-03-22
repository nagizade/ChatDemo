package com.nagizade.chatdemo.AppTabs;


import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.Toolbar;

import com.nagizade.chatdemo.Adapters.ContactsAdapter;
import com.nagizade.chatdemo.ChatActivity;
import com.nagizade.chatdemo.Contact;
import com.nagizade.chatdemo.ItemClickListener;
import com.nagizade.chatdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactsFragment extends Fragment {

    private List<Contact> contacts = new ArrayList<>();
    private RecyclerView recyclerView;
    private ContactsAdapter cAdapter;


    public ContactsFragment() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View contactsFragment = inflater.inflate(R.layout.fragment_contacts, container, false);

        recyclerView = (RecyclerView) contactsFragment.findViewById(R.id.contacts_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //When user clicks on a Contact we will send him to ChatActivity with clicked Contact details
        ItemClickListener listener = new ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                intent.putExtra("Contact name",contacts.get(position).getContact_name());
                intent.putExtra("Contact number",contacts.get(position).getContact_number());
                startActivity(intent);
            }
        };

        cAdapter = new ContactsAdapter(contacts,listener);
        recyclerView.setAdapter(cAdapter);


        // Showing all contacts from phone. We will come back here later for adding contact picture
        ContentResolver cr = getActivity().getContentResolver(); //Activity/Application android.content.Context
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if(cursor.moveToFirst())
        {
            do
            {
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));

                if(Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0)
                {
                    Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",new String[]{ id }, null);
                    while (pCur.moveToNext())
                    {
                        String contactName = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                        String contactNumber = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                        Contact contact = new Contact(contactName,contactNumber);
                        contacts.add(contact);
                        break;
                    }
                    pCur.close();
                }

            } while (cursor.moveToNext()) ;
        }


        // Inflate the layout for this fragment
        return contactsFragment;


    }
}