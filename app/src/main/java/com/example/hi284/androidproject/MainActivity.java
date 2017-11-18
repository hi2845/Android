package com.example.hi284.androidproject;


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

        Cursor cursor = mDbHelper.getAllRest();

        android.widget.SimpleCursorAdapter adapter = new android.widget.SimpleCursorAdapter(getApplicationContext(),
                R.layout.item2, cursor, new String[]{
                ResContract.Rests._ID,
                ResContract.Rests.REST_NAME,
                ResContract.Rests.REST_NUM,
                ResContract.Rests.REST_ADDR},
                new int[]{R.id._id, R.id.name, R.id.phone,R.id.address}, 0);


        ListView listv = (ListView)findViewById(R.id.listview);
        listv.setAdapter(adapter);

        listv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Adapter adapter = adapterView.getAdapter();

                mId.setText(((Cursor)adapter.getItem(i)).getString(0));
                mName.setText(((Cursor)adapter.getItem(i)).getString(1));
                mPhone.setText(((Cursor)adapter.getItem(i)).getString(2));
                mAddress.setText(((Cursor)adapter.getItem(i)).getString(3));
            }
        });
        listv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
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






