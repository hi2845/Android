package com.example.hi284.androidproject;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jkm21 on 2017-11-16.
 */

public class RestAdapter extends BaseAdapter {

    List restaurnat;
    private Context mContext;
    private int mResource;
    private ArrayList<RestItem> mItems = new ArrayList<RestItem>();

    public RestAdapter(List restaurant,Context context) {

        this.mContext = context;
        this.restaurnat=restaurnat;

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
        return this.restaurnat.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        Holder holder = null;
        if (view == null)
        { view = new LinearLayout(mContext);
        ((LinearLayout) view).setOrientation(LinearLayout.HORIZONTAL);
        TextView tvId = new TextView(mContext);
        TextView tvName = new TextView(mContext);
        TextView tvPic = new TextView(mContext);
        TextView tvPhone = new TextView(mContext);

        ( (LinearLayout) view).addView(tvId);
        ( (LinearLayout) view).addView(tvName);
        ( (LinearLayout) view).addView(tvPhone);
        ( (LinearLayout) view).addView(tvPic);

        holder = new Holder();
        holder.tvId = tvId;
        holder.tvName = tvName;
        holder.tvPic = tvPic;
        holder.tvPhone = tvPhone;
        view.setTag(holder);
        }
        else { holder = (Holder) view.getTag();}
        RestaurantClass person = (RestaurantClass) getItem(i);
        holder.tvId.setText(person.get_id() + "");
        holder.tvName.setText(person.getName());
        holder.tvPhone.setText(person.getPhone());
        holder.tvPic.setText(person.getPic() + "");

        final RestaurantClass restaurantClass = (RestaurantClass) getItem(i);
        holder.tvId.setText(person.get_id() + "");
        holder.tvName.setText(person.getName());
        holder.tvPhone.setText(person.getPhone());
        holder.tvPic.setText(person.getAddress());



        return view; }

        }

class RestItem {
    public String resPicPath;
    public String resName;
    public String resAddr;
    public String resNum;
    //Bitmap resPic;

    RestItem(String aresName, String aresNum, String aresAddr) {
        resName = aresName;
        resNum = aresNum;
        resAddr = aresAddr;
    }
}



class Holder
{
    public TextView tvId;
    public TextView tvName;
    public TextView tvPhone;
    public TextView tvPic;
}