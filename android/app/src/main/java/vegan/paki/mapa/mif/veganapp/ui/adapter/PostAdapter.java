package vegan.paki.mapa.mif.veganapp.ui.adapter;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseFile;

import java.util.ArrayList;
import java.util.List;

import vegan.paki.mapa.mif.veganapp.R;
import vegan.paki.mapa.mif.veganapp.core.model.dto.PostDTO;
import vegan.paki.mapa.mif.veganapp.util.RxImageLoader;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> implements View.OnClickListener, View.OnLongClickListener {

    public interface ClickListener {
        public abstract void onClick(int index);

        public abstract boolean onLongClick(int index);
    }

    private List<PostDTO> mItems = new ArrayList<PostDTO>();
    private ClickListener mListener;

    public PostAdapter(List<PostDTO> data, ClickListener listener) {
        mItems = data;
        mListener = listener;
    }

    @Override
    public void onClick(View view) {
        if (mListener != null) {
            mListener.onClick((Integer) view.getTag());
        }
    }

    @Override
    public boolean onLongClick(View view) {
        if (mListener != null) {
            mListener.onLongClick((Integer) view.getTag());
        }
        return false;
    }

    public void set(List<PostDTO> data) {
        mItems.clear();
        mItems.addAll(data);
        notifyDataSetChanged();
    }

    public PostDTO getItem(int index) {
        return mItems.get(index);
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {

        public PostViewHolder(View itemView) {
            super(itemView);
            root = itemView;
            title = (TextView) itemView.findViewById(R.id.title);
            author = (TextView) itemView.findViewById(R.id.author);
            image = (ImageView) itemView.findViewById(R.id.image);
        }

        View root;
        TextView title;
        TextView author;
        ImageView image;
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int index) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_post, parent, false);
        return new PostViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, int index) {
        PostDTO item = mItems.get(index);
        holder.root.setTag(index);
        holder.root.setOnClickListener(this);
        holder.root.setOnLongClickListener(this);

        holder.title.setTypeface(Typeface.create("sans-serif", Typeface.NORMAL));
        holder.title.setText(item.getTitle());

        holder.author.setTypeface(Typeface.create("sans-serif", Typeface.NORMAL));
        holder.author.setText(item.getAuthor());

        ParseFile image = item.getImage();
        if (image != null) {
            RxImageLoader.displayImage(image.getUrl(), holder.image).cache().subscribe();
        } else {
            RxImageLoader.displayImage("drawable://"+R.drawable.vegan_placeholder, holder.image).cache().subscribe();
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}
