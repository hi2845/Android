package com.example.hi284.androidproject;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Restaurant extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        // Intent로 받아온 제목을 넣어주는 것으로 수정하기
        TextView tv = (TextView)findViewById(R.id.res_tv);
        tv.setText("성북동면옥집");

        // 어댑터 생성
        ListAdapter adapter = createAdapter();

        // 어댑터 연결
        ListView listv = (ListView)findViewById(R.id.menu_list);
        listv.setAdapter(adapter);
        listv.setDivider(new ColorDrawable(Color.GRAY));
        listv.setDividerHeight(5);
    }

    //*********************** 여기를 고치시면 될 듯 ***********************
    // 따로 어댑터를 만드셔도 됩니다. 이건 모양을 위해 그냥 넣어놓은거라...
    private ListAdapter createAdapter() {
        String[] menues = {"menu1", "menu2"};

        ArrayAdapter<String> adapt = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, menues
        );

        return adapt;
    }
}
