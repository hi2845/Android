package com.example.hi284.androidproject;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class Restaurant extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private DBHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        mDbHelper = new DBHelper(this);

        Intent intent= getIntent();
        String rest_name = intent.getStringExtra("rest_name");
        String rest_number = intent.getStringExtra("rest_number");
        String imgUri = "";
        String rest_address = "";

        Cursor c = mDbHelper.getRest(rest_name, rest_number);
        c.moveToFirst();

        // 4번째 항목인 사진 UriPath를 불러오기
        imgUri = c.getString(c.getColumnIndex("Rest_Pic"));
        Uri imageUri = Uri.parse(imgUri);
        File file = new File(imageUri.getPath());
        try {
            InputStream ims = new FileInputStream(file);
            ImageView imageView = (ImageView) findViewById(R.id.res_img);
            imageView.setImageBitmap(BitmapFactory.decodeStream(ims));
        } catch (FileNotFoundException e) {
            return;
        }

        // 식당이름 불러와 넣기
        TextView tvName = (TextView)findViewById(R.id.res_tv);
        tvName.setText(rest_name);

        // 식당연락처 불러와 넣기
        TextView tvNum = (TextView)findViewById(R.id.res_num);
        tvNum.setText(rest_number);

        // 식당주소 불러와 넣기
        rest_address = c.getString(c.getColumnIndex("Rest_Address"));
        TextView tvAddr = (TextView)findViewById(R.id.res_addr);
        tvAddr.setText(rest_address);

        ImageButton ib = (ImageButton) findViewById(R.id.call_btn);
        ib.setImageResource(R.drawable.call);
        ib.setScaleType(ImageButton.ScaleType.FIT_XY);

        // 어댑터 생성
        ListAdapter adapters = createAdapter();

        final ArrayList<MyItem> data = new ArrayList<>();
        data.add(new MyItem(R.drawable.mu1,"물냉면","9000원"));
        data.add(new MyItem(R.drawable.mu2,"비빔냉면","9000원"));
        data.add(new MyItem(R.drawable.mu3,"왕갈비탕","12000원"));
        data.add(new MyItem(R.drawable.mu4,"갈비","진 6만원 선 5만원 미 4만원"));

        // 어댑터 연결
        ListView listv = (ListView)findViewById(R.id.res_menu);
        listv.setAdapter(adapters);
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

    // 전화걸기 Intent
    public void callButtonClicked(View v) {
        TextView textv = (TextView)findViewById(R.id.res_num);
        String call = textv.getText().toString();
        Intent intent1 = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+call));
        startActivity(intent1);
    }
}
