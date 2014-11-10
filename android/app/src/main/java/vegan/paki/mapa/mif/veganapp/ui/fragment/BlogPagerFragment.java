package vegan.paki.mapa.mif.veganapp.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vegan.paki.mapa.mif.veganapp.R;
import vegan.paki.mapa.mif.veganapp.ui.adapter.BlogPagerAdapter;
import vegan.paki.mapa.mif.veganapp.ui.view.SlidingTabLayout;

/**
 * Created by Mantas on 11/6/2014.
 */
public class BlogPagerFragment extends Fragment implements NavigationItem {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_blog_sliding, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        ViewPager mViewPager = (ViewPager) view.findViewById(R.id.viewpager);

        mViewPager.setAdapter(new BlogPagerAdapter(getChildFragmentManager()));

        SlidingTabLayout mSlidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setCustomTabView(R.layout.tab_indicator, android.R.id.text1);
        mSlidingTabLayout.setSelectedIndicatorColors(getResources().getColor(R.color.vegan_accent_color));
        mSlidingTabLayout.setDistributeEvenly(true);
        mSlidingTabLayout.setViewPager(mViewPager);
    }

    @Override
    public int getTitleResId() {
        return R.string.blog_title;
    }

    @Override
    public int getIconResId() {
        return 0;
    }
}
