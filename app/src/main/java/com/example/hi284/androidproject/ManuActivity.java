package com.example.hi284.androidproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ManuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manu);

        ImageView iv = (ImageView)findViewById(R.id.imageView1);
        TextView tvTitle = (TextView)findViewById(R.id.textView1);
        TextView tvArtist = (TextView)findViewById(R.id.textView2);


        Intent intent = getIntent(); // 보내온 Intent를 얻는다
        iv.setImageResource(intent.getIntExtra("img", 0));
        tvTitle.setText(intent.getStringExtra("name"));
        tvArtist.setText(intent.getStringExtra("price"));

    }
}
