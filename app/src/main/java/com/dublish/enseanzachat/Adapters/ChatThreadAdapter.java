package com.dublish.enseanzachat.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dublish.enseanzachat.Adapters.ViewHolders.TextMessageViewHolder;
import com.dublish.enseanzachat.DataHolder;
import com.dublish.enseanzachat.Model.Message;
import com.dublish.enseanzachat.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mohamed Nabil Mohamed (Nobel) on 2/26/2019.
 * byte code SA
 * m.nabil.fci2015@gmail.com
 */
public class ChatThreadAdapter extends RecyclerView.Adapter<TextMessageViewHolder> {

    List<Message> messages;

    public ChatThreadAdapter(List<Message> messages) {
        this.messages = messages;
    }

    private final static int INCOMING=1;
    private final static int OUTGOING=2;

    @Override
    public int getItemViewType(int position) {
        Message message=messages.get(position);
        if(message.getSenderId().equals(DataHolder.currentUser.getId()))
            return OUTGOING;
        return INCOMING;

    }

    @NonNull
    @Override
    public TextMessageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view=null;
        if(viewType==INCOMING){
            view=LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.card_item_message_incoming,viewGroup,false);
        }else if(viewType==OUTGOING){
            view=LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.card_item_message_outgoing,viewGroup,false);

        }
        return new TextMessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TextMessageViewHolder textMessageViewHolder, int pos) {
        Message message=messages.get(pos);
        int viewType=getItemViewType(pos);

        textMessageViewHolder.time.setText(message.getTimestamp());
        textMessageViewHolder.content.setText(message.getContent());

        if(viewType==INCOMING)
         textMessageViewHolder.senderName.setText(message.getSenderName());

    }

    public void addMessage(Message message){
        if(messages==null)messages=new ArrayList<>();

        messages.add(message);
        notifyItemInserted(messages.size()-1);
    }

    @Override
    public int getItemCount() {
        if(messages==null)
            return 0;

        return messages.size() ;
    }

}
