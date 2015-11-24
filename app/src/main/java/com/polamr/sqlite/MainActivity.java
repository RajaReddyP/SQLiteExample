package com.polamr.sqlite;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import java.util.List;
import java.util.Random;

public class MainActivity extends ListActivity {
    private DbSource datasource;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        datasource = new DbSource(this);
        datasource.open();

        List<MyData> values = datasource.getAllComments();

        // use the SimpleCursorAdapter to show the
        // elements in a ListView
        ArrayAdapter<MyData> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
    }

    // Will be called via the onClick attribute
    // of the buttons in main.xml
    public void onClick(View view) {
        @SuppressWarnings("unchecked")
        ArrayAdapter<MyData> adapter = (ArrayAdapter<MyData>) getListAdapter();
        MyData data = null;
        switch (view.getId()) {
            case R.id.add:
                String[] comments = new String[] { "DoNut", "honeycomb", "ICS", "JellyBean", "LolliPop" };
                int nextInt = new Random().nextInt(3);
                // save the new comment to the database
                data = datasource.createComment(comments[nextInt]);
                adapter.add(data);
                break;
            case R.id.delete:
                if (getListAdapter().getCount() > 0) {
                    data = (MyData) getListAdapter().getItem(0);
                    datasource.deleteComment(data);
                    adapter.remove(data);
                }
                break;
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        datasource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        datasource.close();
        super.onPause();
    }


}
