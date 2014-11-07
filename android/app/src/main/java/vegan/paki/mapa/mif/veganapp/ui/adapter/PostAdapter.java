package vegan.paki.mapa.mif.veganapp.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import vegan.paki.mapa.mif.veganapp.R;
import vegan.paki.mapa.mif.veganapp.core.model.Post;

/**
 * Created by Panda on 11/6/2014.
 */
public class PostAdapter extends ArrayAdapter<Post>{
    private List<Post> _postList;
    private Context _ctxt;
    private LayoutInflater myInflater;

    public PostAdapter(Context ctxt, List<Post> postList){
        super(ctxt, R.layout.post_layout, postList);
        this._postList = postList;
        this._ctxt = ctxt;
        myInflater = (LayoutInflater) ctxt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView == null){
            convertView = LayoutInflater.from(_ctxt).inflate(R.layout.post_layout, parent, false);
        }
        ImageView image = (ImageView) convertView.findViewById(R.id.post_Image);
        image.setImageResource(_postList.get(position).getPictureId());

        TextView text = (TextView) convertView.findViewById(R.id.post_Name);
        text.setText(_postList.get(position).getName());



        return convertView;
    }


}
