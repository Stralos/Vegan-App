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
import vegan.paki.mapa.mif.veganapp.core.model.dto.CategoryDTO;
import vegan.paki.mapa.mif.veganapp.util.RxImageLoader;

/**
 * Created by Panda on 11/25/2014.
 */
public class MenuAdapter extends BaseAdapter {
    private Context mContext;
    private List<CategoryDTO> categories;


    public MenuAdapter(Context context, List<CategoryDTO> categories){
        mContext = context;
        this.categories = categories;
    }

    public int getCount(){return categories.size();}

    public CategoryDTO getItem(int position){return categories.get(position);}

    public long getItemId(int position){return 0;}

    public View getView(int position, View convertView, ViewGroup parent){
        View view = convertView;
        final ViewHolder holder;
        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            view = LayoutInflater.from(mContext).inflate(R.layout.list_item_category, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        CategoryDTO categoryDTO = getItem(position);
        holder.mTitle.setText(categoryDTO.getName());
        ParseFile image = categoryDTO.getImage();
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