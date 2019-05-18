package br.com.victorcatao.amaki.ui.base;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.victorcatao.amaki.R;
import br.com.victorcatao.amaki.ui.widget.ProgressButton;

public abstract class SimpleBaseRecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_EMPTY = 3;
    private static final int TYPE_FOOTER = 5;
    private static final int TYPE_INVISIBLE = 4;

    private final int HEADER_SIZE = 1;
    private final int FOOTER_SIZE = 1;
    private final int PLACE_HOLDER_SIZE = 1;
    private Context mContext;

    //FLAGS
    private boolean mAlwaysShowFooter = false;
    private boolean mAlwaysShowHeader = false;

    public SimpleBaseRecyclerViewAdapter(Context context) {
        mContext = context;
    }

    public Context getContext() {
        return mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_INVISIBLE:
                return getInvisibleViewHolder(parent);
            case TYPE_EMPTY:
                return getEmptyViewHolder(parent);
            case TYPE_HEADER:
                return getHeaderViewHolder(parent);
            case TYPE_FOOTER:
                return getFooterViewHolder(parent);
            default:
                return getItemViewHolder(parent);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (getDisplayableItemsCount() == 0
                && isPlaceHolderPosition(position)) {
            return TYPE_EMPTY;
        } else if (hasHeader() && isPositionHeader(position)) {
            return TYPE_HEADER;
        } else if (hasFooter() && isPositionFooter(position)) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    private boolean isPlaceHolderPosition(int position) {
        return (hasHeader() && mAlwaysShowHeader && position == 1) ||
                (!hasHeader() && position == 0) ||
                (!mAlwaysShowHeader && position == 0);
    }

    @Override
    public int getItemCount() {
        if (getDisplayableItemsCount() == 0) {
            return PLACE_HOLDER_SIZE + (mAlwaysShowHeader ? HEADER_SIZE : 0) + (mAlwaysShowFooter ? FOOTER_SIZE : 0);
        } else {
            return getDisplayableItemsCount() + (hasFooter() ? FOOTER_SIZE : 0) + (hasHeader() ? HEADER_SIZE : 0);
        }
    }

    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        onBindRecyclerViewHolder(holder, getRealPosition(position));
        if (holder instanceof PlaceHolderVH) {
            if (holder.getItemViewType() == TYPE_EMPTY) {
                ((PlaceHolderVH) holder).bind(getEmptyLabel(getContext()), getEmptyIcon(getContext()), null);
            }
        }
    }

    public abstract int getDisplayableItemsCount();

    public abstract void onBindRecyclerViewHolder(ViewHolder holder, int position);

    //ITEM METHODS
    protected abstract ViewHolder getItemViewHolder(ViewGroup parent);

    //FOOTER METHODS
    private ViewHolder getFooterViewHolder(ViewGroup parent) {
        return null;
    }

    //HEADER METHODS
    private ViewHolder getHeaderViewHolder(ViewGroup parent) {
        return null;
    }

    //EMPTY METHODS
    private String getEmptyLabel(Context context) {
        return context.getString(R.string.placeholder_empty_label);
    }

    private Drawable getEmptyIcon(Context context) {
        return ContextCompat.getDrawable(context, R.drawable.ic_default_empty);
    }

    private ViewHolder getEmptyViewHolder(ViewGroup parent) {
        View sectionView = LayoutInflater.from(parent.getContext()).inflate(R.layout.placeholder_default_general, parent, false);
        return new PlaceHolderVH(sectionView);
    }

    private ViewHolder getInvisibleViewHolder(ViewGroup parent) {
        View sectionView = LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_layout, parent, false);
        return new EmptyPlaceHolderVH(sectionView);
    }

    //GETTERS
    private int getRealPosition(int position) {
        return position - (hasHeader() ? HEADER_SIZE : 0);
    }

    private final boolean hasHeader() {
        try {
            return getHeaderViewHolder(null) != null;
        } catch (NullPointerException e) {
            return true;
        }
    }

    private final boolean hasFooter() {
        try {
            return getFooterViewHolder(null) != null;
        } catch (NullPointerException e) {
            return true;
        }
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    private boolean isPositionFooter(int position) {
        return position == getItemCount() - 1;
    }

    //VIEW HOLDERS
    public class EmptyPlaceHolderVH extends ViewHolder {

        EmptyPlaceHolderVH(View itemView) {
            super(itemView);
        }
    }

    public class PlaceHolderVH extends ViewHolder {
        private TextView vLabel;
        private ImageView vIcon;
        private ProgressButton vRefreshButton;

        PlaceHolderVH(View v) {
            super(v);
            vLabel = itemView.findViewById(R.id.item_default_label);
            vIcon = itemView.findViewById(R.id.item_default_icon);
            vRefreshButton = itemView.findViewById(R.id.item_default_button);
        }

        void bind(String emptyMsg, Drawable drawable, View.OnClickListener refreshClickListener) {
            vLabel.setText(emptyMsg);
            if (drawable == null) {
                vIcon.setVisibility(View.GONE);
            } else {
                Drawable tintedDrawable = drawable.mutate();
                tintedDrawable.setColorFilter(ContextCompat.getColor(itemView.getContext(), R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP);
                vIcon.setImageDrawable(tintedDrawable);
            }

            if (refreshClickListener != null) {
                vRefreshButton.setVisibility(View.VISIBLE);
                vRefreshButton.setOnClickListener(refreshClickListener);
            }
        }
    }
}