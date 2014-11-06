package vegan.paki.mapa.mif.veganapp.util;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import de.keyboardsurfer.android.widget.crouton.Configuration;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import vegan.paki.mapa.mif.veganapp.R;

/**
 * Created by Mantas on 10/14/2014.
 */
public class CroutonUtil {

    private final TextView text;
    private final Button leftButton;
    private final Button rightButton;
    private final RelativeLayout root;
    private CharSequence mLeftButtonMessage;

    public void setColor(int color) {
        this.mColor = color;
    }

    /**
     * Listener for actions of the undo bar.
     */
    public static abstract class ButtonListener {

        /**
         * Will be fired when button is clicked.
         */
        public abstract void onClick();

        public void onLeftClick() {

        }

    }



    public enum Type {
        INFINITY_WITH_BUTTON,
        INFINITY_WITH_BUTTONS;
    }
    private final Activity mActivity;
    private final Type mType;

    private final View mCroutonView;
    private Crouton mCrouton;
    private CharSequence mMessage;
    private CharSequence mButtonMessage;
    private ButtonListener mButtonListener;
    private int mColor;

    private CroutonUtil(Activity activity, Type type) {
        mActivity = activity;
        mType = type;
        mCroutonView = LayoutInflater.from(activity).inflate(R.layout.crouton_with_buttons, null);
        text = (TextView) mCroutonView.findViewById(R.id.tv);
        leftButton = (Button) mCroutonView.findViewById(R.id.leftButton);
        rightButton = (Button) mCroutonView.findViewById(R.id.rightButton);
        root = (RelativeLayout) mCroutonView.findViewById(R.id.root);
        if (mType == Type.INFINITY_WITH_BUTTONS) {
            leftButton.setVisibility(View.VISIBLE);
        } else {
            leftButton.setVisibility(View.GONE);
        }
    }

    public void setButtonListener(ButtonListener buttonListener) {
        this.mButtonListener = buttonListener;
    }

    public void setMessage(CharSequence message) {
        this.mMessage = message;
    }

    public void setButtonMessage(CharSequence buttonMessage) {
        this.mButtonMessage = buttonMessage;
    }

    public void setLeftButtonMessage(CharSequence buttonMessage) {
        this.mLeftButtonMessage = buttonMessage;
    }

    public void hide() {
        mCroutonView.clearFocus();
        mCrouton.hide();
    }

    public boolean isShown() {
        return mCroutonView.isShown();
    }

    public CroutonUtil show() {
        root.setBackgroundColor(mColor);
        text.setText(mMessage);
        rightButton.setText(mButtonMessage);
        leftButton.setText(mLeftButtonMessage);
        mCroutonView.setClickable(true);
        mCroutonView.setFocusable(true);
        mCroutonView.setFocusableInTouchMode(true);

        mCrouton = Crouton.make(mActivity, mCroutonView, (ViewGroup) getActionBarView(mActivity));
        Configuration.Builder config = new Configuration.Builder();
        config.setDuration(Configuration.DURATION_INFINITE);
        mCrouton.setConfiguration(config.build());
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCroutonView.clearFocus();
                mCrouton.hide();
                if (mButtonListener != null) {
                    mButtonListener.onClick();
                }
            }
        });
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCroutonView.clearFocus();
                mCrouton.hide();
                if (mButtonListener != null) {
                    mButtonListener.onLeftClick();
                }
            }
        });
        mCroutonView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    mCrouton.hide();
                }
            }
        });
        mCrouton.show();
        mCroutonView.requestFocus();
        return this;
    }

    public static class Builder {

        private final Activity mActivity;
        private final Type mType;
        private CharSequence mMessage;
        private CharSequence mRightButtonMessage;
        private CharSequence mLeftButtonMessage;
        private ButtonListener mButtonListener;
        private int mColor = -1;

        /**
         * Constructor using the {@link android.app.Activity} in which the undo bar will be
         * displayed
         */
        public Builder(Activity activity, Type type) {
            mActivity = activity;
            mType = type;
        }

        /**
         * Sets the message to be displayed on the left of the undo bar.
         */
        public Builder setMessage(int messageResId) {
            mMessage = mActivity.getString(messageResId);
            return this;
        }

        /**
         * Sets the message to be displayed on the left of the undo bar.
         */
        public Builder setMessage(CharSequence message) {
            mMessage = message;
            return this;
        }

        /**
         * Sets the message to be displayed on the left of the undo bar.
         */
        public Builder setButtonMessage(int messageResId) {
            mRightButtonMessage = mActivity.getString(messageResId);
            return this;
        }

        /**
         * Sets the message to be displayed on the left of the undo bar.
         */
        public Builder setButtonMessage(CharSequence message) {
            mRightButtonMessage = message;
            return this;
        }

        /**
         * Sets the message to be displayed on the left of the undo bar.
         */
        public Builder setLeftButtonMessage(int messageResId) {
            mLeftButtonMessage = mActivity.getString(messageResId);
            return this;
        }

        /**
         * Sets the message to be displayed on the left of the undo bar.
         */
        public Builder setLeftButtonMessage(CharSequence message) {
            mLeftButtonMessage = message;
            return this;
        }

        /**
         * Sets the {@link ButtonListener CroutonUtil.Listener}.
         */
        public Builder setListener(ButtonListener undoListener) {
            mButtonListener = undoListener;
            return this;
        }

        public Builder setColor(int color) {
            mColor = color;
            return this;
        }

        /**
         * Creates an {@link CroutonUtil} instance with this Builder's
         * configuration.
         */
        public CroutonUtil build() {
            CroutonUtil crouton = new CroutonUtil(mActivity, mType);
            crouton.setButtonListener(mButtonListener);
            crouton.setMessage(mMessage);
            crouton.setButtonMessage(mRightButtonMessage);
            crouton.setLeftButtonMessage(mLeftButtonMessage);
            if (mColor == -1) {
                mColor = mActivity.getResources().getColor(android.R.color.holo_red_light);
            }
            crouton.setColor(mColor);
            return crouton;
        }

        public CroutonUtil show() {
            return build().show();
        }
    }

    private View getActionBarView(Activity activity) {
        Window window = activity.getWindow();
        View v = window.getDecorView();
        int resId = activity.getResources().getIdentifier("action_bar_container", "id", "android");
        return v.findViewById(resId);
    }
}
