package com.example.foodhouse.model;

import com.google.firebase.database.ServerValue;

public class Comment {

    private String userComments;
    private String publisherID;
    private Object timestamp;

    public Comment() {
    }

    public Comment(String userComments, String publisherID) {
        this.userComments = userComments;
        this.publisherID = publisherID;
        this.timestamp = ServerValue.TIMESTAMP;
    }

    public Comment(String userComments, String publisherID, Object timestamp) {
        this.userComments = userComments;
        this.publisherID = publisherID;

        this.timestamp = timestamp;
    }

    public String getUserComments() {
        return userComments;
    }

    public void setUserComments(String userComments) {
        this.userComments = userComments;
    }

    public String getPublisherID() {
        return publisherID;
    }

    public void setPublisherID(String publisherID) {
        this.publisherID = publisherID;
    }


    public Object getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Object timestamp) {
        this.timestamp = timestamp;
    }
}
