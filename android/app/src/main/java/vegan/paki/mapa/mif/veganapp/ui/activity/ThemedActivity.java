package vegan.paki.mapa.mif.veganapp.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import vegan.paki.mapa.mif.veganapp.util.ThemeUtils;

/**
 * Created by Mantas on 11/10/2014.
 */
public class ThemedActivity extends ActionBarActivity {

    private ThemeUtils mThemeUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mThemeUtils = new ThemeUtils(this);
        setTheme(mThemeUtils.getCurrent());
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mThemeUtils.isChanged()) {
            setTheme(mThemeUtils.getCurrent());
            recreate();
        }
    }
}
