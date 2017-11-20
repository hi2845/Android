package com.example.hi284.androidproject;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ManuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manu);


        //레스토랑 액티비티에서 값을 받아 메뉴 프레그먼트 실행한다
        ManuFragment manus = new ManuFragment();
        manus.setpic(getIntent().getIntExtra("img",0));
        manus.setname(getIntent().getStringExtra("name"));
        manus.setprice(getIntent().getStringExtra("price"));
        getSupportFragmentManager().beginTransaction().replace(R.id.manu, manus).commit();

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            Drawable drawable = getDrawable(R.drawable.ic_keyboard_arrow_left_black_24dp);
            if(drawable != null) {
                drawable.setTint(Color.WHITE);
                actionBar.setHomeAsUpIndicator(drawable);
            }
        }

    }
}
