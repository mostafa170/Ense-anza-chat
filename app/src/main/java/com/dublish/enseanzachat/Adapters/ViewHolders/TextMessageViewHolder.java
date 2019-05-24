package com.dublish.enseanzachat.Adapters.ViewHolders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.dublish.enseanzachat.R;


/**
 * Created by Mohamed Nabil Mohamed (Nobel) on 2/26/2019.
 * byte code SA
 * m.nabil.fci2015@gmail.com
 */
public class TextMessageViewHolder extends RecyclerView.ViewHolder {

    public TextView senderName;
    public TextView content;
    public TextView time;

    public TextMessageViewHolder(@NonNull View itemView) {
        super(itemView);
        senderName =itemView.findViewById(R.id.sender_name);
        content=itemView.findViewById(R.id.content);
        time=itemView.findViewById(R.id.time);

    }
}
