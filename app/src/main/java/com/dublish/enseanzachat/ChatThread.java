package com.dublish.enseanzachat;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.dublish.enseanzachat.Adapters.ChatThreadAdapter;
import com.dublish.enseanzachat.Base.BaseActivity;
import com.dublish.enseanzachat.FireBaseUtils.MessagesDao;
import com.dublish.enseanzachat.Model.Message;
import com.dublish.enseanzachat.Model.Room;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatThread extends BaseActivity implements View.OnClickListener {

    protected RecyclerView recyclerView;
    protected ImageButton send;
    protected EditText message;
    ChatThreadAdapter adapter;
    LinearLayoutManager layoutManager;

    static Room currentRoom;
    Query messagesQuery;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_chat_thread);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(currentRoom.getName());
        initView();
        layoutManager=new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        adapter=new ChatThreadAdapter(null);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        messagesQuery= MessagesDao.getMessagesByRoomId(currentRoom.getId());
        messagesQuery.addChildEventListener(messagesListener);

    }


    ChildEventListener messagesListener=new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot,
                                 @Nullable String s) {
            Message message=dataSnapshot.getValue(Message.class);
            Log.e("message",message.getContent());
            adapter.addMessage(message);

        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.send) {
            String content=message.getText().toString();

            Message message = new Message();
            message.setContent(content);
            message.setSenderId(DataHolder.currentUser.getId());
            message.setSenderName(DataHolder.currentUser.getUserName());
            message.setRoomId(currentRoom.getId());
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
            String currentTimeStamp=simpleDateFormat.format(new Date());
            message.setTimestamp(currentTimeStamp);
            MessagesDao
                    .sendMessage(message,onSuccessListener,onFailureListener);

        }
    }

    OnSuccessListener onSuccessListener=new OnSuccessListener() {
        @Override
        public void onSuccess(Object o) {
            message.setText("");

        }
    };
    OnFailureListener onFailureListener=new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            Toast.makeText(activity, R.string.try_again, Toast.LENGTH_SHORT).show();

        }
    };

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        send = (ImageButton) findViewById(R.id.send);
        send.setOnClickListener(ChatThread.this);
        message = (EditText) findViewById(R.id.message);
    }
}
