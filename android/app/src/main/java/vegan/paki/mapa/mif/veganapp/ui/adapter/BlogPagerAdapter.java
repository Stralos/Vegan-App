package vegan.paki.mapa.mif.veganapp.ui.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

import vegan.paki.mapa.mif.veganapp.ui.fragment.BlogFragment;

/**
 * Created by Mantas on 11/10/2014.
 */
public class BlogPagerAdapter extends FragmentStatePagerAdapter {

    public BlogPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        BlogFragment frag = new BlogFragment();
        Bundle args = new Bundle();
        if (position == 0) {
            args.putBoolean("local", false);
        } else {
            args.putBoolean("local", true);
        }
        frag.setArguments(args);
        return frag;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public int getItemPosition(Object object){
        return PagerAdapter.POSITION_NONE;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "All";
        }
        return "Favourite";
    }
}
