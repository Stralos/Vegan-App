package vegan.paki.mapa.mif.veganapp.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import vegan.paki.mapa.mif.veganapp.R;
import vegan.paki.mapa.mif.veganapp.ui.adapter.BlogPagerAdapter;

import vegan.paki.mapa.mif.veganapp.ui.adapter.MenuAdapter;
import vegan.paki.mapa.mif.veganapp.ui.view.SlidingTabLayout;

/**
 * Created by Panda on 11/24/2014.
 */
public class MenuPagerFragment extends Fragment implements NavigationItem {

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu_layout, container, false);

        GridView gridView = (GridView) view.findViewById(R.id.menu_view);
        gridView.setAdapter(new MenuAdapter(gridView.getContext()));

        return view;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }



    public int getTitleResId(){ return R.string.menu_navigation_drawer;}
    public int getIconResId(){ return 0;}
}
