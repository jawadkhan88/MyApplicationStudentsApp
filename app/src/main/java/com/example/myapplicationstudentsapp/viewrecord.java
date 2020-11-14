package com.example.myapplicationstudentsapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.database.SQLException;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class viewrecord extends AppCompatActivity {
    String ID="";
    String name="";
    String age="";
    String address="";

    MyDBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewrecord);

                ID = this.getIntent().getStringExtra("ID");
        ((TextView)findViewById(R.id.textViewID)).setText(ID);
        db = new MyDBHandler(this);
        String selectQuery = "select * from student where id = ID";
            SQLiteDatabase sdb = db.getReadableDatabase();
            Cursor cursor = sdb.rawQuery(selectQuery, null);
        String id=ID;
            if(cursor.moveToFirst())
            {
                do{

                    name = cursor.getString(1);
                    age = String.valueOf(cursor.getInt(2));
                    address = cursor.getString(3);

                }while (cursor.moveToNext());
            }


        ((TextView)findViewById(R.id.editTextID)).setText(ID);
        ((TextView)findViewById(R.id.editTextName)).setText(name);
        ((TextView)findViewById(R.id.editTextAge)).setText(age);
        ((TextView)findViewById(R.id.editTextAddress)).setText(address);
    }
    public void btnEdit_Click(View view)
    {
        Intent intent = new Intent(this, edit_record.class);
        intent.putExtra("ID", ID);
        this.startActivity(intent);

    }

    public void btnDelete_Click(View view)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Delete the record?").setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                String msg  = "Record deleted...";
                SQLiteDatabase sdb = db.getWritableDatabase();
                try
                {
                    sdb.delete("student","id="+ID, null);
                }
                catch(SQLException ex)
                {
                    msg = ex.getMessage();
                    Toast.makeText(viewrecord.this, msg, Toast.LENGTH_LONG).show();
                }

                Intent intent = new Intent(viewrecord.this, show_all_record.class);
                //ViewRecordActivity.this.startActivity(intent);

            }
        }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    public void btnBack_Click(View view)
    {
        this.startActivity(new Intent(this,show_all_record.class));
    }
}
