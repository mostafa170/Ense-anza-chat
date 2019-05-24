package com.dublish.enseanzachat.Model;

public class Message {
    String id;
    String content;
    String roomId;
    String senderId;
    String senderName;
    String timestamp;

    public Message() {
    }

    public Message(String content, String roomId, String senderId, String senderName, String timestamp) {
        this.content = content;
        this.roomId = roomId;
        this.senderId = senderId;
        this.senderName = senderName;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
