package vegan.paki.mapa.mif.veganapp.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import vegan.paki.mapa.mif.veganapp.R;

/**
 * Created by Panda on 11/25/2014.
 */
public class MenuAdapter extends BaseAdapter {
    int[] images = {R.drawable.vegan_placeholder, R.drawable.vegan_placeholder, R.drawable.vegan_placeholder, R.drawable.vegan_placeholder};
    private Context mContext;

    public MenuAdapter(Context context){
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
            iv.setLayoutParams(new GridView.LayoutParams(300,300));
            //iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            //iv.setPadding(8,8,8,8);
        }
        iv.setImageResource(images[position]);
        return iv;
    }



}