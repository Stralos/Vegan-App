package vegan.paki.mapa.mif.veganapp.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.List;

import vegan.paki.mapa.mif.veganapp.R;
import vegan.paki.mapa.mif.veganapp.core.model.dto.CategoryDTO;
import vegan.paki.mapa.mif.veganapp.util.RxImageLoader;

/**
 * Created by Panda on 11/25/2014.
 */
public class MenuAdapter extends BaseAdapter {
    int[] images = {R.drawable.vegan_placeholder, R.drawable.vegan_placeholder, R.drawable.vegan_placeholder, R.drawable.vegan_placeholder};
    private Context mContext;
    List<CategoryDTO> categories;


    public MenuAdapter(Context context, List<CategoryDTO> categories){
        mContext = context;
        this.categories = categories;
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

        //RxImageLoader.displayImage(categories.get(position).getImage().getUrl(), iv).cache().subscribe();
        return iv;
    }



}