package br.com.victorcatao.amaki.utils.helpers;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.com.victorcatao.amaki.R;

public class FeedbackHelper {

    private final int RESOURCE_NOT_DEFINED = 0;

    private Context mContext;

    private View.OnClickListener mOnTryAgainClicked;
    private View mLoadingView, mPlaceholderView;
    private ViewGroup mContainer;

    private LayoutInflater mInflater;

    private HashMap<View, Integer> mViewsVisibility;
    private List<View> mViewsToHideOrShow = new ArrayList<>();

    //Resources
    @ColorRes
    private int mProgressBarColor = RESOURCE_NOT_DEFINED, mIconsColor = RESOURCE_NOT_DEFINED;
    @DrawableRes
    private int mErrorDrawable = RESOURCE_NOT_DEFINED, mEmptyDrawable = RESOURCE_NOT_DEFINED;
    private String mErrorMsg, mEmptyMsg;

    public FeedbackHelper(Context context, ViewGroup containerToShowFeedback, View.OnClickListener onTryAgainClicked) {
        mOnTryAgainClicked = onTryAgainClicked;
        mContext = context;
        mContainer = containerToShowFeedback;
        mInflater = LayoutInflater.from(mContext);


        mViewsVisibility = new HashMap<>();
        for (int i = 0; i < containerToShowFeedback.getChildCount(); i++) {
            mViewsToHideOrShow.add(containerToShowFeedback.getChildAt(i));
            mViewsVisibility.put(containerToShowFeedback.getChildAt(i), containerToShowFeedback.getChildAt(i).getVisibility());
        }
    }

    public void updateVisibility(View view, Integer visibility) {
        mViewsVisibility.put(view, visibility);
    }

    private FeedbackHelper setProgressBarColor(@ColorRes int progressBarColor) {
        mProgressBarColor = progressBarColor;
        return this;
    }

    public FeedbackHelper setIconsColor(@ColorRes int iconsColor) {
        mIconsColor = iconsColor;
        return this;
    }

    public FeedbackHelper setErrorMsg(String errorMsg) {
        mErrorMsg = errorMsg;
        return this;
    }

    public FeedbackHelper setEmptyMsg(String emptyMsg) {
        mEmptyMsg = emptyMsg;
        return this;
    }

    public FeedbackHelper setErrorDrawable(@DrawableRes int errorDrawable) {
        mErrorDrawable = errorDrawable;
        return this;
    }

    public FeedbackHelper setEmptyDrawable(@DrawableRes int emptyDrawable) {
        mEmptyDrawable = emptyDrawable;
        return this;
    }

    public void startLoading() {
        hideViews();
        addLoading();
        removeErrorPlaceHolder();
    }

    public void showEmptyPlaceHolder() {
        hideViews();

        addErrorPlaceHolder();
        bind(mEmptyMsg == null ? mContext.getString(R.string.placeholder_empty_label) : mEmptyMsg,
                mEmptyDrawable == RESOURCE_NOT_DEFINED ? R.drawable.ic_default_empty : mEmptyDrawable, null);
    }

    public void showErrorPlaceHolder() {
        hideViews();

        addErrorPlaceHolder();
        bind(mErrorMsg == null ? mContext.getString(R.string.placeholder_error_label) : mErrorMsg,
                mErrorDrawable == RESOURCE_NOT_DEFINED ? R.drawable.ic_default_request_error : mErrorDrawable, mOnTryAgainClicked);
    }

    public void dismissFeedback() {
        showViews();
        removeErrorPlaceHolder();
        removeLoading();
    }

    private void addLoading() {
        mLoadingView = mInflater.inflate(R.layout.placeholder_default_loading, mContainer, false);
        ((ProgressBar) mLoadingView.findViewById(R.id.default_progressbar)).getIndeterminateDrawable().mutate().setColorFilter(ContextCompat.getColor(mContext, mProgressBarColor == RESOURCE_NOT_DEFINED ? R.color.colorPrimary : mProgressBarColor), PorterDuff.Mode.SRC_ATOP);
        mContainer.addView(mLoadingView);
    }

    private void addErrorPlaceHolder() {
        mPlaceholderView = mInflater.inflate(R.layout.placeholder_default_general, mContainer, false);
        mContainer.addView(mPlaceholderView);
    }

    private void removeLoading() {
        mContainer.removeView(mLoadingView);
    }

    private void removeErrorPlaceHolder() {
        mContainer.removeView(mPlaceholderView);
    }

    private void hideViews() {
        for (View view : mViewsToHideOrShow) {
            view.setVisibility(View.GONE);
        }
    }

    @SuppressWarnings("ResourceType")
    private void showViews() {
        for (View view : mViewsToHideOrShow) {
            view.setVisibility(mViewsVisibility.get(view));
        }
    }

    private void bind(String msg, int drawable, View.OnClickListener refreshClickListener) {
        //Msg
        ((TextView) mPlaceholderView.findViewById(R.id.item_default_label)).setText(msg);

        //Drawable
        Drawable tintedDrawable = ContextCompat.getDrawable(mContext, drawable).mutate();
        tintedDrawable.setColorFilter(ContextCompat.getColor(mContext, mIconsColor == RESOURCE_NOT_DEFINED ? R.color.colorPrimary : mIconsColor), PorterDuff.Mode.SRC_ATOP);
        ((ImageView) mPlaceholderView.findViewById(R.id.item_default_icon)).setImageDrawable(tintedDrawable);

        //Button
        if(refreshClickListener != null){
            (mPlaceholderView.findViewById(R.id.item_default_button)).setVisibility(View.VISIBLE);
            (mPlaceholderView.findViewById(R.id.item_default_button)).setOnClickListener(refreshClickListener);
        }
    }
}
