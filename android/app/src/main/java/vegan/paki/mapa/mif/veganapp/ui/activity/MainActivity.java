package vegan.paki.mapa.mif.veganapp.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;

import java.util.ArrayList;
import java.util.List;

import vegan.paki.mapa.mif.veganapp.R;
import vegan.paki.mapa.mif.veganapp.ui.fragment.BlogFragment;
import vegan.paki.mapa.mif.veganapp.ui.fragment.BlogSlidingFragment;
import vegan.paki.mapa.mif.veganapp.ui.fragment.NavigationDrawerFragment;
import vegan.paki.mapa.mif.veganapp.ui.fragment.NavigationFragment;


public class MainActivity extends ThemedActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks{

    private CharSequence mTitle;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private List<NavigationFragment> mNavFragments = new ArrayList<NavigationFragment>();
    private List<Fragment> mFragments = new ArrayList<Fragment>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        BlogFragment blogFragment = new BlogFragment();
        BlogSlidingFragment blogSlidingFragment = new BlogSlidingFragment();
        mFragments.add(blogFragment);
        mFragments.add(blogSlidingFragment);
        mNavFragments.add(blogFragment);
        mNavFragments.add(blogSlidingFragment);
        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            BlogFragment fragment = new BlogFragment();
            transaction.replace(R.id.container, fragment);
            transaction.commit();
        }

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationDrawerFragment mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mNavigationDrawerFragment.setUp(mNavFragments, R.id.navigation_drawer, mDrawerLayout);

    }


    @Override
    public void onNavigationDrawerItemSelected(int position) {
        Fragment fragment = new BlogFragment();
        try {
            fragment = mFragments.get(position);
        } catch (Exception e) {}

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.commit();
    }
}
