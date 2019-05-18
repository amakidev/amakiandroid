package br.com.victorcatao.amaki.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.design.widget.TextInputEditText;
import android.support.v7.content.res.AppCompatResources;
import android.util.AttributeSet;

import br.com.victorcatao.amaki.R;


public class CompatDrawableEditText extends TextInputEditText {
    public CompatDrawableEditText(Context context) {
        super(context);
    }

    public CompatDrawableEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
    }

    public CompatDrawableEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
    }

    void initAttrs(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray attributeArray = context.obtainStyledAttributes(
                    attrs,
                    R.styleable.CompatDrawableEditText);

            Drawable drawableLeft = null;
            Drawable drawableRight = null;
            Drawable drawableBottom = null;
            Drawable drawableTop = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                drawableLeft = attributeArray.getDrawable(R.styleable.CompatDrawableEditText_drawableLeftCompat);
                drawableRight = attributeArray.getDrawable(R.styleable.CompatDrawableEditText_drawableRightCompat);
                drawableBottom = attributeArray.getDrawable(R.styleable.CompatDrawableEditText_drawableBottomCompat);
                drawableTop = attributeArray.getDrawable(R.styleable.CompatDrawableEditText_drawableTopCompat);
            } else {
                final int drawableLeftId = attributeArray.getResourceId(R.styleable.CompatDrawableEditText_drawableLeftCompat, -1);
                final int drawableRightId = attributeArray.getResourceId(R.styleable.CompatDrawableEditText_drawableRightCompat, -1);
                final int drawableBottomId = attributeArray.getResourceId(R.styleable.CompatDrawableEditText_drawableBottomCompat, -1);
                final int drawableTopId = attributeArray.getResourceId(R.styleable.CompatDrawableEditText_drawableTopCompat, -1);

                if (drawableLeftId != -1)
                    drawableLeft = AppCompatResources.getDrawable(context, drawableLeftId);
                if (drawableRightId != -1)
                    drawableRight = AppCompatResources.getDrawable(context, drawableRightId);
                if (drawableBottomId != -1)
                    drawableBottom = AppCompatResources.getDrawable(context, drawableBottomId);
                if (drawableTopId != -1)
                    drawableTop = AppCompatResources.getDrawable(context, drawableTopId);
            }
            setCompoundDrawablesWithIntrinsicBounds(drawableLeft, drawableTop, drawableRight, drawableBottom);
            attributeArray.recycle();
        }
    }
}
