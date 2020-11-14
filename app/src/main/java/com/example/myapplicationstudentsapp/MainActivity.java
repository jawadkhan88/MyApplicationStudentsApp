package com.example.myapplicationstudentsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    String id="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void btninsertinfo(View view)
    {
        this.startActivity(new Intent(this, InsertRecordActivity.class));
    }
    public void editRecordClick(View view )
    {
        Intent intent = new Intent(this, edit_record.class);
        intent.putExtra("ID", id);
        this.startActivity(intent);
    }
    public void btnShowAllRecordActivity_Click(View view)
    {
        Intent intent = new Intent(this, show_all_record.class);
        //intent.putExtra("ID", id);
        this.startActivity(intent);

    }

    public void btnSearchRecordActivity_Click(View view)
    {
        this.startActivity(new Intent(this, search_record.class));

    }
}
