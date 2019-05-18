package br.com.victorcatao.amaki.ui.base

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v7.content.res.AppCompatResources
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import br.com.victorcatao.amaki.R

abstract class SimpleRecyclerViewAdapter(val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    enum class Type {
        HEADER,
        ITEM,
        EMPTY,
        FOOTER,
        ERROR
    }

    companion object {
        const val HEADER_SIZE = 1
        const val FOOTER_SIZE = 1
        const val PLACE_HOLDER_SIZE = 1
    }

    //HEADER AND FOOTER FLAGS
    var alwaysShowFooter = false
    var alwaysShowHeader = false

    //DEFAULT MESSAGES
    var emptyMessage: String = context.getString(R.string.placeholder_empty_label)
    var emptyIcone: Drawable? = AppCompatResources.getDrawable(context, R.drawable.ic_default_empty)
    var emptyTitle: String = context.getString(R.string.placeholder_empty_title)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
//            Type.EMPTY.ordinal -> getEmptyViewHolder(parent)
//            Type.HEADER.ordinal -> getHeaderViewHolder(parent)
//            Type.FOOTER.ordinal -> getFooterViewHolder(parent)
//            Type.ERROR.ordinal -> getFooterViewHolder(parent)
            else -> getItemViewHolder(parent)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItemsCount() == 0 && isPlaceHolderPosition(position)) {
            Type.EMPTY.ordinal
        } else if (hasHeader() && isPositionHeader(position)) {
            Type.HEADER.ordinal
//        } else if (hasFooter() && isPositionFooter(position)) {
//            Type.FOOTER.ordinal
        } else {
            Type.ERROR.ordinal
        }
    }

    override fun getItemCount(): Int {
        return if (getItemsCount() == 0) {
            PLACE_HOLDER_SIZE + (if (alwaysShowHeader) HEADER_SIZE else 0) + if (alwaysShowFooter) FOOTER_SIZE else 0
        } else {
            0
//            getItemsCount() + (if (hasFooter()) FOOTER_SIZE else 0) + if (hasHeader()) HEADER_SIZE else 0
        }
    }

    abstract fun getItemsCount(): Int?

    abstract fun onBindRecyclerViewHolder(holder: RecyclerView.ViewHolder, position: Int)

    protected abstract fun getItemViewHolder(parent: ViewGroup): RecyclerView.ViewHolder

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        onBindRecyclerViewHolder(holder, getRealPosition(position))
        if (holder is PlaceHolderVH) {
            if (holder.itemViewType == Type.EMPTY.ordinal) {
//                holder.bind(getEmptyLabel(context), getEmptyIcon(context), null)
            }
        }
    }

    private fun isPositionHeader(position: Int): Boolean = position == 0

    private fun isPositionFooter(position: Int): Boolean = position == itemCount?.minus(1) ?: false

    private fun isPlaceHolderPosition(position: Int): Boolean {
        return hasHeader() && alwaysShowHeader && position == 1 ||
                !hasHeader() && position == 0 ||
                !alwaysShowHeader && position == 0
    }

    fun hasHeader(): Boolean {
        try {
//            return getHeaderViewHolder(null) != null
        } catch (e: NullPointerException) {
        }
        return true
    }

    inner class PlaceHolderVH(v: View) : RecyclerView.ViewHolder(v) {
        private val vLabel: TextView
        private val vIcon: ImageView
        private val vRefreshButton: Button

        init {
            vLabel = itemView.findViewById<View>(R.id.item_default_label) as TextView
            vIcon = itemView.findViewById<View>(R.id.item_default_icon) as ImageView
            vRefreshButton = itemView.findViewById<View>(R.id.item_default_button) as Button
        }

        fun bind(emptyMsg: String, drawable: Drawable?, refreshClickListener: View.OnClickListener?) {
            vLabel.text = emptyMsg
            if (drawable == null) {
                vIcon.visibility = View.GONE
            } else {
                val tintedDrawable = drawable.mutate()
                tintedDrawable.setColorFilter(ContextCompat.getColor(itemView.context, R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP)
                vIcon.setImageDrawable(tintedDrawable)
            }

            if (refreshClickListener != null) {
                vRefreshButton.visibility = View.VISIBLE
                vRefreshButton.setOnClickListener(refreshClickListener)
            }
        }
    }
}