package com.example.hi284.androidproject;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class RestFragment extends Fragment {
    //메뉴 데이터저장
    int pic;
    String name;
    String price;
    //레스토랑 데이터 저장
    int id;
    String rName;
    String rPhone;
    String rAddr;


    public RestFragment() {
        // Required empty public constructor
    }

    //인텐트 된 데이터 받아오기
    public void setId(int i){id=i;}
    public void setrName(String i){rName=i;}
    public void setrPhone(String i){rPhone=i;}
    public void setrAddr(String i){rAddr=i;}



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_rest, container, false);


        ImageButton ib = (ImageButton) view.findViewById(R.id.call_btn);
        ib.setImageResource(R.drawable.call);
        ib.setScaleType(ImageButton.ScaleType.FIT_XY);

        // string.xml 리소스파일에서 식당이미지 불러오기
        ImageView im = (ImageView) view.findViewById(R.id.res_img);
        im.setImageResource(R.drawable.res1);
        im.setScaleType(ImageView.ScaleType.FIT_XY);


        // 리소스파일에서 식당이름 불러오기
        TextView tvName = (TextView) view.findViewById(R.id.res_tv);
        tvName.setText(rName);


        // string.xml 리소스파일에서 식당연락처 불러오기
        TextView tvNum = (TextView) view.findViewById(R.id.res_num);
        tvNum.setText(rPhone);


        // string.xml 리소스파일에서 식당주소 불러오기
        TextView tvAddr = (TextView) view.findViewById(R.id.res_addr);
        tvAddr.setText(rAddr);



        // 어댑터 생성
        final ArrayList<MyItem> data = new ArrayList<>();
        data.add(new MyItem(R.drawable.mu1,"물냉면","9000원"));
        data.add(new MyItem(R.drawable.mu2,"비빔냉면","9000원"));
        data.add(new MyItem(R.drawable.mu3,"왕갈비탕","12000원"));
        data.add(new MyItem(R.drawable.mu4,"갈비","진 6만원 선 5만원 미 4만원"));



        // 어댑터 연결
        ListView listv = (ListView) view.findViewById(R.id.res_menu);
        listv.setAdapter(new MyAdapter(getActivity(), R.layout.item,data));
        listv.setDivider(new ColorDrawable(Color.GRAY));
        listv.setDividerHeight(5);
        listv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                pic=data.get(position).mIcon;
                name=data.get(position).nName;
                price=data.get(position).nprice;

                //아이템이 클릭되면 정보반환
                Activity activity = getActivity();
                ((OnTitleSelectedListener)activity).onTitleSelected(pic,name,price, rName,rPhone,rAddr);


            }
        });



        // Inflate the layout for this fragment
        return view;
    }

    //메뉴 데이터와 레스토랑 데이터 반환
    public interface OnTitleSelectedListener {
        public void onTitleSelected(int pic,String name, String price, String rName, String rPhone, String rAddr);
    }
}


