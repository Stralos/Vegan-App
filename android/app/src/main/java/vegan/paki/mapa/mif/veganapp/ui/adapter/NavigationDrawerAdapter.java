package vegan.paki.mapa.mif.veganapp.ui.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vegan.paki.mapa.mif.veganapp.R;
import vegan.paki.mapa.mif.veganapp.ui.fragment.NavigationFragment;
import vegan.paki.mapa.mif.veganapp.util.Utils;

public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.ShortcutViewHolder> implements View.OnClickListener, View.OnLongClickListener {

    public interface ClickListener {
        public abstract void onClick(int index);

        public abstract boolean onLongClick(int index);
    }

    private List<NavigationFragment> mItems = new ArrayList<NavigationFragment>();
    private int mCheckedPos = -1;
    private ClickListener mListener;
    private int selectionColor;
    private int bodyText;

    public NavigationDrawerAdapter(Context context, List<NavigationFragment> data, ClickListener listener) {
        mItems = data;
        mListener = listener;
        selectionColor = Utils.resolveColor(context, R.attr.selection_text);
        bodyText = Utils.resolveColor(context, R.attr.body_text);
    }

    @Override
    public void onClick(View view) {
        if (mListener != null) {
            mListener.onClick((Integer) view.getTag());
        }
    }

    @Override
    public boolean onLongClick(View view) {
        mListener.onLongClick((Integer) view.getTag());
        return false;
    }

    public void set(List<NavigationFragment> data) {
        mItems.clear();
        mItems.addAll(data);
        notifyDataSetChanged();
    }

    public void setCheckedPos(int index) {
        mCheckedPos = index;
        notifyDataSetChanged();
    }

    public NavigationFragment getItem(int index) {
        return mItems.get(index);
    }

    public static class ShortcutViewHolder extends RecyclerView.ViewHolder {

        public ShortcutViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView;
        }

        TextView title;
    }

    @Override
    public ShortcutViewHolder onCreateViewHolder(ViewGroup parent, int index) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_drawer, parent, false);
        return new ShortcutViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ShortcutViewHolder holder, int index) {
        NavigationFragment item = mItems.get(index);
        holder.title.setTag(index);
        holder.title.setOnClickListener(this);
        holder.title.setOnLongClickListener(this);
        holder.title.setActivated(mCheckedPos == index);
        holder.title.setTextColor(mCheckedPos == index ? selectionColor : bodyText);

        if (mCheckedPos == index) {
            holder.title.setTypeface(Typeface.create("sans-serif", Typeface.NORMAL));
        } else {
            holder.title.setTypeface(Typeface.create("sans-serif-light", Typeface.NORMAL));
        }
        holder.title.setText(item.getTitleResId());
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}
