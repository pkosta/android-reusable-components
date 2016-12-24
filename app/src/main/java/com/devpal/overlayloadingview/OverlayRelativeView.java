package com.devpal.overlayloadingview;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RotateDrawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

/*
 * Created by Palash on 23/12/16.
 */

public class OverlayRelativeView extends RelativeLayout
{

    private View mOverLayView;

    private ProgressBar mProgressBar;

    private Boolean mShowLoader = true;        // default is true.

    private int mOverLayColor;

    private int mLoaderColor;

    private Drawable mLoaderDrawable;

    private Drawable mOverlayBackgroundDrawable;

    private int mLoaderPosition;

    private float mLoaderMargin;

    public OverlayRelativeView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // inflating the layout.
        LayoutInflater inflater = LayoutInflater.from(context);
        RelativeLayout layoutView =  (RelativeLayout) inflater.inflate(R.layout.layout_overlay, this);

        // initializing the views..
        mOverLayView = layoutView.findViewById(R.id.layout_overlay);
        mProgressBar = (ProgressBar) layoutView.findViewById(R.id.progressBar);

        // read the attributes from the custom views.
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.OverlayRelativeView,
                0, 0);

        try {
            // reading the values.
            mShowLoader = a.getBoolean(R.styleable.OverlayRelativeView_showLoader, true);
            mOverLayColor = a.getColor(R.styleable.OverlayRelativeView_overlayColor, Color.parseColor("#80000000"));  // default is semi-transparent color.
            mLoaderColor = a.getColor(R.styleable.OverlayRelativeView_loaderColor,
                    ContextCompat.getColor(context,R.color.colorAccent));

            mLoaderDrawable = a.getDrawable(R.styleable.OverlayRelativeView_loaderDrawable);    //TODO: - provide default progress drawable.
            mOverlayBackgroundDrawable = a.getDrawable(R.styleable.OverlayRelativeView_overlayBackground);
            mLoaderPosition = a.getInteger(R.styleable.OverlayRelativeView_loaderPosition,0);    // Default is top position.
            mLoaderMargin = a.getDimension(R.styleable.OverlayRelativeView_loaderMargin,80F);

            // setting up the user interface...
            setUpUserInterface();

            hideOverlay();

        } finally {
            a.recycle();
        }

    }

    /**
     * setting up the user interface after getting all required attributes from the custom view.
     */
    private void setUpUserInterface() {

        if(mOverLayView != null) {
            mOverLayView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    // disable the backward layout.
                }
            });
        }

        if(mOverlayBackgroundDrawable != null) {
            mOverLayView.setBackground(mOverlayBackgroundDrawable);
        } else {
            mOverLayView.setBackgroundColor(mOverLayColor);
        }


        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mProgressBar.getLayoutParams();

        // setting loader position.
        switch (mLoaderPosition) {
            case 0:
                //top
                params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                params.topMargin = (int) mLoaderMargin;
                break;
            case 1:
                //bottom
                params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                params.bottomMargin = (int) mLoaderMargin;
                break;
            case 2:
                //left
                params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                params.leftMargin = (int) mLoaderMargin;
                break;
            case 3:
                //right
                params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                params.rightMargin = (int) mLoaderMargin;
                break;
            case 4:
                //center
                params.addRule(RelativeLayout.CENTER_IN_PARENT);

                break;
            default:
                //top
                params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                params.topMargin = (int) mLoaderMargin;
        }

        mProgressBar.setLayoutParams(params);


        Drawable drawable = mProgressBar.getProgressDrawable();
        if(mLoaderDrawable != null) {

            mProgressBar.setProgressDrawable(mLoaderDrawable);

        } else if(mLoaderColor != 0) {

            // Check if we're running on Android 5.0 or higher
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                // Call some material design APIs here
                mProgressBar.setIndeterminateTintList(ColorStateList.valueOf(mLoaderColor));

            } else {

                // Implement this feature without material design
                if(drawable instanceof RotateDrawable) {

                    RotateDrawable rotateDrawable = (RotateDrawable) drawable;
                    GradientDrawable gradientDrawable = (GradientDrawable) rotateDrawable.getDrawable();
                    if(gradientDrawable != null) {
                        gradientDrawable.setColor(mLoaderColor);
                    }
                }

            }

        } else {
            // already set by the layout file...set it again the default one.
            mProgressBar.setProgressDrawable(drawable);
        }

    }

    public void showOverlay() {

        mOverLayView.setVisibility(VISIBLE);
        if(mShowLoader) mProgressBar.setVisibility(VISIBLE);
    }

    public void hideOverlay() {

        mOverLayView.setVisibility(GONE);
        mProgressBar.setVisibility(GONE);
    }

    /*public void setTargetLayout(ViewGroup viewGroup) {

        if(viewGroup instanceof RelativeLayout) {

            RelativeLayout relativeLayout = new RelativeLayout(getContext());
            relativeLayout.setLayoutParams(viewGroup.getLayoutParams());
            //mOverLayView.bringToFront();

            viewGroup.addView(mOverLayView,0);
            //viewGroup.addView(mProgressBar,4);

            mOverLayView = viewGroup.findViewById(1);
            mOverLayView.bringToFront();
            mOverLayView.invalidate();
            ((View)mOverLayView.getParent()).invalidate();
            mOverLayView.getParent().requestLayout();

        } else if(viewGroup instanceof LinearLayout) {

            LinearLayout linearLayout = new LinearLayout(getContext());
            linearLayout.setLayoutParams(viewGroup.getLayoutParams());

        } else if(viewGroup instanceof FrameLayout) {

            FrameLayout frameLayout = new FrameLayout(getContext());
            frameLayout.setLayoutParams(viewGroup.getLayoutParams());

        } else {

            // throw the exception.

        }

    }*/

}
