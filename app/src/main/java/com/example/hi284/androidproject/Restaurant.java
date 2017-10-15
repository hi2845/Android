package com.example.hi284.androidproject;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Restaurant extends AppCompatActivity implements AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        //전화걸기 Intent
        /*int pre_call = R.string.res1_num;
        String call = Integer.toString(pre_call);
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(call));
        startActivity(intent);*/

        ImageButton ib = (ImageButton) findViewById(R.id.call_btn);
        ib.setImageResource(R.drawable.call);
        ib.setScaleType(ImageButton.ScaleType.FIT_XY);

        // string.xml 리소스파일에서 식당이미지 불러오기
        ImageView im = (ImageView)findViewById(R.id.res_img);
        im.setImageResource(R.drawable.res1);
        im.setScaleType(ImageView.ScaleType.FIT_XY);

        // 리소스파일에서 식당이름 불러오기
        TextView tv = (TextView)findViewById(R.id.res_tv);
        tv.setText(R.string.res1_name);

        // string.xml 리소스파일에서 식당주소 불러오기
        tv = (TextView)findViewById(R.id.res_addr);
        tv.setText(R.string.res1_addr);

        // string.xml 리소스파일에서 식당연락처 불러오기
        tv = (TextView)findViewById(R.id.res_num);
        tv.setText(R.string.res1_num);

        // 어댑터 생성
        ListAdapter adapter = createAdapter();

        final ArrayList<MyItem> data = new ArrayList<>();
        data.add(new MyItem(R.drawable.mu1,"물냉면","9000원"));
        data.add(new MyItem(R.drawable.mu2,"비빔냉면","9000원"));
        data.add(new MyItem(R.drawable.mu3,"왕갈비탕","12000원"));
        data.add(new MyItem(R.drawable.mu4,"갈비","진 6만원 선 5만원 미 4만원"));

        // 어댑터 연결
        ListView listv = (ListView)findViewById(R.id.res_menu);
        listv.setAdapter(adapter);
        listv.setDivider(new ColorDrawable(Color.GRAY));
        listv.setDividerHeight(5);
        listv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Intent intent = new Intent(
                        getApplicationContext(), // 현재화면의 제어권자
                        ManuActivity.class); // 다음넘어갈 화면

                // intent 객체에 데이터를 실어서 보내기
                // 리스트뷰 클릭시 인텐트 (Intent) 생성하고 position 값을 이용하여 인텐트로 넘길값들을 넘긴다
                intent.putExtra("img", data.get(position).mIcon);
                intent.putExtra("name", data.get(position).nName);
                intent.putExtra("price", data.get(position).nprice);

                startActivity(intent);
            }
        });
    } // end of onCreate





    //*********************** 여기를 고치시면 될 듯 ***********************
    // 따로 어댑터를 만드셔도 됩니다. 이건 모양을 위해 그냥 넣어놓은거라...
    private ListAdapter createAdapter() {
        ArrayList<MyItem> data = new ArrayList<>();
        data.add(new MyItem(R.drawable.mu1,"물냉면","9000원"));
        data.add(new MyItem(R.drawable.mu2,"비빔냉면","9000원"));
        data.add(new MyItem(R.drawable.mu3,"왕갈비탕","12000원"));
        data.add(new MyItem(R.drawable.mu4,"갈비","진 6만원 선 5만원 미 4만원"));

        MyAdapter adapt
                = new MyAdapter(this, R.layout.item,data);



        return adapt;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
