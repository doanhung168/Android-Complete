package com.doanhung.sqlite.data.model;

public class Todo {
    private long mId;
    private String mContent;
    private String mPlace;

    public Todo() {
    }

    public Todo(long id, String content) {
        this.mId = id;
        this.mContent = content;
    }

    public Todo(long mId, String mContent, String place) {
        this.mId = mId;
        this.mContent = mContent;
        this.mPlace = place;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        this.mId = id;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        this.mContent = content;
    }

    public String getPlace() {
        return mPlace;
    }

    public void setPlace(String place) {
        this.mPlace = place;
    }
}
