package com.polamr.sqlite;

/**
 * Created by Rajareddy on 24/11/15.
 */
public class MyData {

    private long id;
    private String data;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getComment() {
        return data;
    }

    public void setComment(String comment) {
        this.data = comment;
    }

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return data;
    }
}
