package vegan.paki.mapa.mif.veganapp.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;

import vegan.paki.mapa.mif.veganapp.R;

public class Utils {

    public static int resolveDrawable(Context context, int drawable) {
        TypedArray a = context.obtainStyledAttributes(new int[]{drawable});
        int resId = a.getResourceId(0, 0);
        a.recycle();
        return resId;
    }

    public static int resolveColor(Context context, int color) {
        TypedArray a = context.obtainStyledAttributes(new int[]{color});
        int resId = a.getColor(0, context.getResources().getColor(R.color.vegan_color));
        a.recycle();
        return resId;
    }

    public static void lockOrientation(Activity context) {
        int currentOrientation = context.getResources().getConfiguration().orientation;
        if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            context.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        } else {
            context.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        }
    }

    public static void unlockOrientation(Activity context) {
        context.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
    }

    public interface ClickListener {
        void onPositive(int which, View view);
    }

    public static void showConfirmDialog(Activity context, int title, int message, Object replacement, final ClickListener callback) {
        new MaterialDialog.Builder(context)
                .positiveColorRes(R.color.vegan_accent_color)
                .theme(ThemeUtils.getDialogTheme(context))
                .title(title)
                .content(message, replacement)
                .positiveText(android.R.string.ok)
                .negativeText(android.R.string.cancel)
                .callback(new MaterialDialog.Callback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        callback.onPositive(0, null);
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                    }
                })
                .build().show();
    }

    public static void showErrorDialog(Activity context, int message, Exception e) {
        showErrorDialog(context, context.getString(message, e.getMessage()));
    }

    public static void showErrorDialog(final Activity context, final String message) {
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new MaterialDialog.Builder(context)
                        .positiveColorRes(R.color.vegan_accent_color)
                        .theme(ThemeUtils.getDialogTheme(context))
                        .title(R.string.error)
                        .content(message)
                        .positiveText(android.R.string.ok)
                        .callback(new MaterialDialog.SimpleCallback() {
                            @Override
                            public void onPositive(MaterialDialog dialog) {
                            }
                        })
                        .build().show();
            }
        });
    }

    public static ProgressDialog showProgressDialog(Activity context, int message, ProgressDialog.OnCancelListener cancelListener) {
        ProgressDialog mDialog = new ProgressDialog(context);
        mDialog.setCancelable(cancelListener != null);
        mDialog.setOnCancelListener(cancelListener);
        mDialog.setMessage(context.getString(message));
        mDialog.setIndeterminate(true);
        mDialog.show();
        return mDialog;
    }

    public static ProgressDialog showProgressDialog(Activity context, int message) {
        return showProgressDialog(context, message, null);
    }

    /*public static void showInputDialog(Activity context, int title, int hint, String prefillInput, final InputCallback callback) {
        final View view = context.getLayoutInflater().inflate(R.layout.dialog_input, null);
        MaterialDialog.Builder dialog = new MaterialDialog.Builder(context)
                .positiveColorRes(R.color.vegan_accent_color)
                .theme(ThemeUtils.getDialogTheme(context))
                .title(title)
                .positiveText(android.R.string.ok)
                .negativeText(android.R.string.cancel)
                .callback(new MaterialDialog.Callback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        if (callback != null) {
                            EditText input = (EditText) view.findViewById(R.id.input);
                            callback.onInput(input.getText().toString().trim());
                        }
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                    }
                }).customView(view);
        final EditText input = (EditText) view.findViewById(R.id.input);
        if (hint != 0) input.setHint(hint);
        if (prefillInput != null) input.append(prefillInput);
        MaterialDialog alert = dialog.build();
        alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (callback instanceof InputCancelCallback)
                    ((InputCancelCallback) callback).onCancel();
            }
        });
        alert.show();
    }*/


}