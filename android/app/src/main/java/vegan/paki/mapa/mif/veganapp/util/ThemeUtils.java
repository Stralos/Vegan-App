package vegan.paki.mapa.mif.veganapp.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.afollestad.materialdialogs.Theme;

import vegan.paki.mapa.mif.veganapp.R;

public class ThemeUtils {

    public ThemeUtils(Activity context) {
        mContext = context;
        isChanged(); // invalidate stored booleans
    }

    private Context mContext;
    private boolean darkMode;
    private boolean trueBlack;

    public static boolean isDarkMode(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getBoolean("dark_mode", false);
    }

    public static boolean isTrueBlack(Context context) {
        if (!isDarkMode(context)) return false;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getBoolean("true_black", false);
    }

    public boolean isChanged() {
        boolean darkTheme = isDarkMode(mContext);
        boolean blackTheme = isTrueBlack(mContext);

        boolean changed = darkMode != darkTheme || trueBlack != blackTheme;
        darkMode = darkTheme;
        trueBlack = blackTheme;
        return changed;
    }

    public static Theme getDialogTheme(Context context) {
        if (isDarkMode(context) || isTrueBlack(context)) return Theme.DARK;
        else return Theme.LIGHT;
    }

    public int getCurrent() {
        if (darkMode) {
            //TODO
            return R.style.Theme_Vegan;
        } else {
            return R.style.Theme_Vegan;
        }
    }
}
