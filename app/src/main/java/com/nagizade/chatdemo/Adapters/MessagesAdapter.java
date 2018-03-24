package com.nagizade.chatdemo.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nagizade.chatdemo.ItemClickListener;
import com.nagizade.chatdemo.MessageModel;
import com.nagizade.chatdemo.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by redShaman on 3/23/2018.
 */
public class MessagesAdapter extends RecyclerView.Adapter {

    private List<MessageModel> messagesList;
    private ItemClickListener  mListener;
    private RelativeLayout chatLayout;
    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mListener = itemClickListener;
    }

   /* public class MessagesHolder extends RecyclerView.ViewHolder {

     //   private ItemClickListener mListener;

        MessagesHolder(View itemView, ItemClickListener listener) {
            super(itemView);
            messageC = (TextView) itemView.findViewById(R.id.textView3);
            messageT = (TextView) itemView.findViewById(R.id.textView4);

            mListener = listener;
        }
    }*/

    public MessagesAdapter(List<MessageModel> messagesList) {
        this.messagesList = messagesList;
           //  mListener = listener;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
         View itemView;

        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_sent, parent, false);
            return new SentMessageHolder(itemView);
        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_received, parent, false);
            return new GetMessageHolder(itemView);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MessageModel messageModel = messagesList.get(position);
        switch (holder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                ((SentMessageHolder) holder).bind(messageModel,position);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((GetMessageHolder) holder).bind(messageModel,position);
        }
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

    /**
      * If we sent message then it must be in left.
      * If our friend sent it it must be in right
      */

    private class SentMessageHolder extends RecyclerView.ViewHolder {
        public TextView messageC,messageT;

        SentMessageHolder(View itemView) {
            super(itemView);
            messageC = (TextView) itemView.findViewById(R.id.textView3);
            messageT = (TextView) itemView.findViewById(R.id.textView4);
        }

        void bind(MessageModel messageModel,int position) {
            messageModel = messagesList.get(position);
            messageC.setText(messageModel.getMessageContent());
            messageT.setText(formatDate(String.valueOf(messageModel.getTimestamp())));
        }
    }

    private class GetMessageHolder extends RecyclerView.ViewHolder {
        public TextView messageC,messageT;

        GetMessageHolder(View itemView) {
            super(itemView);
            messageC = (TextView) itemView.findViewById(R.id.textView3);
            messageT = (TextView) itemView.findViewById(R.id.textView4);
        }

        void bind(MessageModel messageModel,int position) {
            messageModel = messagesList.get(position);
            messageC.setText(messageModel.getMessageContent());
            messageT.setText(formatDate(String.valueOf(messageModel.getTimestamp())));
        }
    }

    // Determines the appropriate ViewType according to the sender of the message.
    @Override
    public int getItemViewType(int position) {
        MessageModel messageModel = messagesList.get(position);

        if (messageModel.getUsername().equals("me")) {
            // If the current user is the sender of the message
            return VIEW_TYPE_MESSAGE_SENT;
        } else {
            // If some other user sent the message
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }



    /**
      * Getting total count of messages
     */

    @Override
    public int getItemCount() {
        return messagesList.size();
    }
}