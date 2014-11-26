package vegan.paki.mapa.mif.veganapp.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseFile;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import vegan.paki.mapa.mif.veganapp.R;
import vegan.paki.mapa.mif.veganapp.core.model.dto.FoodDTO;
import vegan.paki.mapa.mif.veganapp.util.RxImageLoader;

/**
 * Created by Panda on 11/25/2014.
 */
public class FoodAdapter extends BaseAdapter {
    private Context mContext;
    private List<FoodDTO> categories;


    public FoodAdapter(Context context, List<FoodDTO> categories){
        mContext = context;
        this.categories = categories;
    }

    public int getCount(){return categories.size();}

    public FoodDTO getItem(int position){return categories.get(position);}

    public long getItemId(int position){return 0;}

    public View getView(int position, View convertView, ViewGroup parent){
        View view = convertView;
        final ViewHolder holder;
        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            view = LayoutInflater.from(mContext).inflate(R.layout.list_item_food, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        FoodDTO foodDTO = getItem(position);
        holder.mTitle.setText(foodDTO.getName());
        ParseFile image = foodDTO.getImage();
        if (image != null) {
            RxImageLoader.displayImage(image.getUrl(), holder.mImage).cache().subscribe();
        } else {
            RxImageLoader.displayImage("drawable://"+R.drawable.vegan_placeholder, holder.mImage).cache().subscribe();
        }

        return view;
    }

    static class ViewHolder {
        @InjectView(R.id.title) TextView mTitle;
        @InjectView(R.id.image) ImageView mImage;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }



}