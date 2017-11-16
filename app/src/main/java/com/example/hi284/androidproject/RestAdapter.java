package com.example.hi284.androidproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by jkm21 on 2017-11-16.
 */

public class RestAdapter extends BaseAdapter {
    private Context mContext;
    private int mResource;
    private ArrayList<RestItem> mItems = new ArrayList<RestItem>();

    public RestAdapter(Context context, int resource, ArrayList<RestItem> items) {
        mContext = context;
        mResource = resource;
        mItems = items;
    }

    public void addItem(RestItem item) {
        mItems.add(item);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(int i) {
        return mItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null) {
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(mResource, viewGroup, false);
        }

        // 식당 이미지 넣기
        ImageView resPic = (ImageView)view.findViewById(R.id.res_img);


        // 식당 이름 넣기
        TextView name = (TextView)view.findViewById(R.id.res_name);
        name.setText(mItems.get(i).resName);

        // 식당 주소 넣기
        TextView addr = (TextView)view.findViewById(R.id.res_addr);
        addr.setText(mItems.get(i).resAddr);

        // 식당 번호 넣기
        TextView number = (TextView)view.findViewById(R.id.res_num);
        number.setText(mItems.get(i).resNum);

        return view;
    }
}
