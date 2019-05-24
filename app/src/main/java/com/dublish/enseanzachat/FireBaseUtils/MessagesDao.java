package com.dublish.enseanzachat.FireBaseUtils;


import com.dublish.enseanzachat.Model.Message;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class MessagesDao {

  public static void sendMessage(Message message,
                                 OnSuccessListener onSuccessListener,
                                 OnFailureListener onFailureListener){
     DatabaseReference messageNode=
             MyDataBase.getMessagesBranch().push();
     message.setId(messageNode.getKey());
     messageNode.setValue(message)
             .addOnSuccessListener(onSuccessListener)
             .addOnFailureListener(onFailureListener);
    }

    public static Query getMessagesByRoomId(String roomId){

        Query query=MyDataBase.getMessagesBranch()
                .orderByChild("roomId")
                .equalTo(roomId);
        return query;


    }

}
