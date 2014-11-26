package com.example.panda.testapplication;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * Created by Panda on 11/23/2014.
 */
public class ImageAdapter extends BaseAdapter {
    int[] images = {R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher};
    private Context mContext;

    public ImageAdapter(Context context){
        mContext = context;
    }

    public int getCount(){return images.length;}

    public Object getItem(int position){return null;}

    public long getItemId(int position){return 0;}

    public View getView(int position, View convertView, ViewGroup parrent){
        ImageView iv;
        if(convertView != null){
            iv = (ImageView) convertView;
        }else{
            iv = new ImageView(mContext);
            iv.setLayoutParams(new GridView.LayoutParams(80,80));
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            iv.setPadding(8,8,8,8);
        }
        iv.setImageResource(images[position]);
        return iv;
    }



}
