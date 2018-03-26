package com.nagizade.chatdemo.AppTabs;


import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.nagizade.chatdemo.Adapters.ChatsViewAdapter;
import com.nagizade.chatdemo.Adapters.DatabaseAdapter;
import com.nagizade.chatdemo.Adapters.MessagesAdapter;
import com.nagizade.chatdemo.ChatActivity;
import com.nagizade.chatdemo.Contact;
import com.nagizade.chatdemo.ItemClickListener;
import com.nagizade.chatdemo.LastMessageModel;
import com.nagizade.chatdemo.MessageModel;
import com.nagizade.chatdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatsFragment extends Fragment{

    private DatabaseAdapter helper;
    private ChatsViewAdapter viewAdapter;
    private List<LastMessageModel> messages = new ArrayList<>();
    private RecyclerView chatsView;
    private ChatsViewAdapter.MyViewHolder holder;
    private TextView contactName;
    private SQLiteDatabase db;



    public ChatsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View chatsFragment = inflater.inflate(R.layout.fragment_chats, container, false);

        helper   = new DatabaseAdapter(getActivity());
        db       = helper.getReadableDatabase();
        messages.addAll(helper.getLastMessages());

        final ItemClickListener listener = new ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Intent intent = new Intent(getActivity(), ChatActivity.class);
//              intent.putExtra("Contact name",contactName.getText().toString());
                intent.putExtra("Contact number",messages.get(position).getUsername());
                intent.putExtra("Contact id",messages.get(position).getProfilePic());
                startActivity(intent);
            }
        };

        chatsView = (RecyclerView) chatsFragment.findViewById(R.id.chatsList);
        viewAdapter = new ChatsViewAdapter(messages,listener);
        RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(getActivity().getApplicationContext());
        chatsView.setLayoutManager(mLayoutManager2);
        chatsView.setItemAnimator(new DefaultItemAnimator());
        chatsView.setAdapter(viewAdapter);

        // Inflate the layout for this fragment
        return chatsFragment;
    }


}
