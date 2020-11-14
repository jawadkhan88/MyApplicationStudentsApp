package com.example.myapplicationstudentsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;

public class show_all_record extends AppCompatActivity implements MyRecyclerViewAdapter.IMyItemClickListener {
    MyDBHandler db;
    MyRecyclerViewAdapter adapter;
    ArrayList<HashMap<String,String>> persons;
    HashMap<String, String> map;
    String selectQuery = "select * from student";
    String activityName = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_record);
        db = new MyDBHandler(this);

        if (this.getIntent() != null) {
            if (this.getIntent().getStringExtra("QUERY") != null) {
                activityName = "SearchRecordActivity";
                selectQuery = this.getIntent().getStringExtra("QUERY");
            }
        }
        SQLiteDatabase sdb = db.getReadableDatabase();
        //Cursors are pointers to the result set within the underlying data. Cursors provide a managed way of controlling your position (row) in the result set of a database query.
        Cursor cursor = sdb.rawQuery(selectQuery, null);

        String id="";
        String name="";
        String age="";
        String address="";

        persons = new ArrayList<>();


        if(cursor.moveToFirst())
        {
            do{
                id = String.valueOf(cursor.getInt(0));
                name = cursor.getString(1);
                age = String.valueOf(cursor.getInt(2));
                address = cursor.getString(3);

                map = new HashMap<>();
                map.put("ID", id);
                map.put("NAME", name);
                map.put("AGE", age);
                map.put("ADDRESS", address);
                persons.add(map);

            }while (cursor.moveToNext());
        }

        RecyclerView recyclerView = findViewById(R.id.recyclerView1);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        // Divider code //

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        // ===========
        adapter = new MyRecyclerViewAdapter(this, persons);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }
    public void onMyItemClick(View view, int position)
    {
        Intent intent = new Intent(this, viewrecord.class);
        intent.putExtra("ID", adapter.getItem(position).get("ID"));
        this.startActivity(intent);
    }

    public void btnBack_Click(View view)
    {
        Intent intent;
        if(activityName.equals("SearchRecordActivity"))
        {
            intent = new Intent(this, search_record.class);
            this.startActivity(intent);
        }
        else
        {
            this.startActivity(new Intent(this, MainActivity.class));
        }
    }
}
