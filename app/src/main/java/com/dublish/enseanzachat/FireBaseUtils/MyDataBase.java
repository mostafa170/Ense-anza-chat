package com.dublish.enseanzachat.FireBaseUtils;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MyDataBase {

   private static FirebaseDatabase firebaseDatabase;

   static FirebaseDatabase getInstance(){
       if(firebaseDatabase==null)
           firebaseDatabase= FirebaseDatabase.getInstance();

       return firebaseDatabase;
   }

   private static final String Users="users";
   public static DatabaseReference getUsersBranch(){
       return getInstance().getReference(Users);
   }

   private static final String Rooms="rooms";
   public static DatabaseReference getRoomsBranch(){
       return getInstance().getReference(Rooms);
   }
   static final String Messages="messages";
   public static DatabaseReference getMessagesBranch(){
       return getInstance().getReference(Messages);
   }

}
