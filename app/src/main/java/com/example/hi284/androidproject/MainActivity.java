package com.example.hi284.androidproject;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    EditText mId;
    EditText mName;
    EditText mPhone;
    EditText mAddress;
    EditText mpic;

    private DBHelper mDbHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mId = (EditText)findViewById(R.id._id);
        mName = (EditText)findViewById(R.id.edit_name);
        mPhone = (EditText)findViewById(R.id.edit_phone);
        mAddress= (EditText)findViewById(R.id.edit_address);
        mpic= (EditText)findViewById(R.id.edit_pic);

        mDbHelper = new DBHelper(this);

        Button button = (Button)findViewById(R.id.insert);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertRest();
                viewAllToListView();
            }
        });

        Button button1 = (Button)findViewById(R.id.delete);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteRest();
                viewAllToListView();
            }
        });

        viewAllToListView();

    }




    private void viewAllToListView() {

        ListView lv= (ListView) findViewById(R.id.listview);

        lv.setVisibility(View.VISIBLE);

        List restaurnat = mDbHelper.getAllPersonData();
        lv.setAdapter(new RestAdapter(restaurnat, MainActivity.this));



        lv.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Restaurant.class);
                //intent.putExtra("_id", RestaurantClass.get_id());

                startActivity(intent); }
        });

        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

    }



    private void deleteRest() {
        EditText _id = (EditText)findViewById(R.id._id);

        long nOfRows = mDbHelper.deleteRest(_id.getText().toString());
        if (nOfRows >0)
            Toast.makeText(this,"Record Deleted", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this,"No Record Deleted", Toast.LENGTH_SHORT).show();
    }

    private void insertRest() {
        EditText name = (EditText)findViewById(R.id.edit_name);
        EditText phone = (EditText)findViewById(R.id.edit_phone);
        EditText address= (EditText)findViewById(R.id.edit_address);
        EditText pic = (EditText)findViewById(R.id.edit_pic);

        long nOfRows = mDbHelper.insertRest(name.getText().toString(),phone.getText().toString(),address.getText().toString(),pic.getText().toString());
        if (nOfRows >0)
            Toast.makeText(this,nOfRows+" Record Inserted", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this,"No Record Inserted", Toast.LENGTH_SHORT).show();
    }



}






