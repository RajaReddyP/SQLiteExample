package com.polamr.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rajareddy on 24/11/15.
 */
public class DbSource {

    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { MySQLiteHelper.DATA_ID,
            MySQLiteHelper.DATA_MSG };

    public DbSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() {
        try {
            database = dbHelper.getWritableDatabase();
        } catch(Exception e) {

        }
    }

    public void close() {
        dbHelper.close();
    }

    public MyData createComment(String comment) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.DATA_MSG, comment);
        long insertId = database.insert(MySQLiteHelper.TABLE_DATA, null,
                values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_DATA,
                allColumns, MySQLiteHelper.DATA_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        MyData newComment = cursorToComment(cursor);
        cursor.close();
        return newComment;
    }

    public void deleteComment(MyData data) {
        long id = data.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_DATA, MySQLiteHelper.DATA_ID
                + " = " + id, null);
    }

    public List<MyData> getAllComments() {
        List<MyData> comments = new ArrayList<MyData>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_DATA,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            MyData comment = cursorToComment(cursor);
            comments.add(comment);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return comments;
    }

    private MyData cursorToComment(Cursor cursor) {
        MyData data = new MyData();
        data.setId(cursor.getLong(0));
        data.setComment(cursor.getString(1));
        return data;
    }
}
