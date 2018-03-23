package com.nagizade.chatdemo.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nagizade.chatdemo.Contact;
import com.nagizade.chatdemo.ItemClickListener;
import com.nagizade.chatdemo.MessageModel;
import com.nagizade.chatdemo.R;

import java.util.List;

/**
 * Created by redShaman on 3/23/2018.
 */
public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessagesHolder> {

    private List<MessageModel> messagesList;
    private ItemClickListener mListener;

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mListener = itemClickListener;
    }

    public class MessagesHolder extends RecyclerView.ViewHolder {
        public TextView messageC,messageT;
     //   private ItemClickListener mListener;

        MessagesHolder(View itemView, ItemClickListener listener) {
            super(itemView);
            messageC = (TextView) itemView.findViewById(R.id.textView3);
            messageT = (TextView) itemView.findViewById(R.id.textView4);
            mListener = listener;
        }
    }

    public MessagesAdapter(List<MessageModel> messagesList) {
        this.messagesList = messagesList;
           //  mListener = listener;
    }
    @Override
    public MessagesAdapter.MessagesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_row,parent,false);

        return new MessagesHolder(itemView,mListener);
    }

    @Override
    public void onBindViewHolder(MessagesAdapter.MessagesHolder holder, int position) {
        MessageModel messageModel = messagesList.get(position);
        holder.messageC.setText(messageModel.getMessageContent());
        holder.messageT.setText(String.valueOf(messageModel.getTimestamp()));
    }

    @Override
    public int getItemCount() {
        return messagesList.size();
    }
}