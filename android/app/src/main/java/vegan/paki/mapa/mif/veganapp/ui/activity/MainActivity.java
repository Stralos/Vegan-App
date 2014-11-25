package vegan.paki.mapa.mif.veganapp.ui.activity;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import vegan.paki.mapa.mif.veganapp.R;
import vegan.paki.mapa.mif.veganapp.ui.fragment.BlogFragment;
import vegan.paki.mapa.mif.veganapp.ui.fragment.BlogPagerFragment;
import vegan.paki.mapa.mif.veganapp.ui.fragment.NavigationDrawerFragment;
import vegan.paki.mapa.mif.veganapp.ui.fragment.NavigationItem;


public class MainActivity extends ThemedActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks{

    private CharSequence mTitle;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private List<NavigationItem> mNavFragments = new ArrayList<NavigationItem>();
    private List<Fragment> mFragments = new ArrayList<Fragment>();
    private Toolbar mActionBarToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        getActionBarToolbar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        BlogPagerFragment blogPagerFragment = new BlogPagerFragment();
        mFragments.add(blogPagerFragment);
        mNavFragments.add(blogPagerFragment);
        if (savedInstanceState == null) {
            switchFragment(mFragments.get(0), true);
        }

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationDrawerFragment mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mNavigationDrawerFragment.setUp(mNavFragments, R.id.navigation_drawer, mDrawerLayout);

    }

    protected Toolbar getActionBarToolbar() {
        if (mActionBarToolbar == null) {
            mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar);
            if (mActionBarToolbar != null) {
                setSupportActionBar(mActionBarToolbar);
            }
        }
        return mActionBarToolbar;
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            super.onBackPressed();
        } else getFragmentManager().popBackStack();
    }


    @Override
    public void onNavigationDrawerItemSelected(int position) {
        Fragment fragment = new BlogFragment();
        try {
            fragment = mFragments.get(position);
        } catch (Exception e) {}

        switchFragment(fragment, true);
    }

    public void switchFragment(Fragment fragment, boolean clearBackStack) {
        if (fragment == null) {
            return;
        }
        if (clearBackStack)
            getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        if (!clearBackStack) transaction.addToBackStack(null);
        try {
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
