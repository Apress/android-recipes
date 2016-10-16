package com.examples.customwidgets;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class TextImageButton extends FrameLayout {

    private ImageView imageView;
    private TextView textView;

    /* Constructors */
    public TextImageButton(Context context) {
        this(context, null);
    }

    public TextImageButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextImageButton(Context context, AttributeSet attrs, int defaultStyle) {
        super(context, attrs, defaultStyle);
        imageView = new ImageView(context, attrs, defaultStyle);
        textView = new TextView(context, attrs, defaultStyle);
        //create layout parameters
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
        //Add the views
        this.addView(imageView, params);
        this.addView(textView, params);

        //Make this view interactive
        setClickable(true);
        setFocusable(true);
        //Set the default system button background
        setBackgroundResource(android.R.drawable.btn_default);

        //If image is present, switch to image mode
        if(imageView.getDrawable() != null) {
            textView.setVisibility(View.GONE);
            imageView.setVisibility(View.VISIBLE);
        } else {
            textView.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.GONE);
        }
    }

    /* Accessors */
    public void setText(CharSequence text) {
        //Switch to text
        textView.setVisibility(View.VISIBLE);
        imageView.setVisibility(View.GONE);
        //Apply text
        textView.setText(text);
    }

    public void setImageResource(int resId) {
        //Switch to image
        textView.setVisibility(View.GONE);
        imageView.setVisibility(View.VISIBLE);
        //Apply image
        imageView.setImageResource(resId);
    }

    public void setImageDrawable(Drawable drawable) {
        //Switch to image
        textView.setVisibility(View.GONE);
        imageView.setVisibility(View.VISIBLE);
        //Apply image
        imageView.setImageDrawable(drawable);
    }
}

