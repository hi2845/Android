package com.example.hi284.androidproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initListView();
    }

    private void initListView() {
        listView = (ListView)findViewById(R.id.rest_list);
    }

    public void sungbuk(View v) {
        Intent intent = new Intent(getApplicationContext(), Restaurant.class);
        startActivity(intent);
    }
}
