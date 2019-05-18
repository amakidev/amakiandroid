package br.com.victorcatao.amaki.ui.home

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.victorcatao.amaki.R
import br.com.victorcatao.amaki.data.remote.models.EstablishmentResponse
import br.com.victorcatao.amaki.ui.base.SimpleBaseRecyclerViewAdapter
import br.com.victorcatao.amaki.utils.extensions.loadImage
import kotlinx.android.synthetic.main.item_establishment.view.*

class EstablishmentListAdapter(context: Context, private val onItemClickListener: OnItemClickListener, private val onItemLongClickListener: OnItemLongClickListener) :
        SimpleBaseRecyclerViewAdapter(context) {

    var mList: List<EstablishmentResponse> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getDisplayableItemsCount() = mList.size

    override fun onBindRecyclerViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            holder.bind(mList[position], onItemClickListener, onItemLongClickListener)
        }
    }

    override fun getItemViewHolder(parent: ViewGroup?): RecyclerView.ViewHolder {
        //TODO change layout to inflate
        val itemView = LayoutInflater.from(parent!!.context).inflate(R.layout.item_establishment, parent, false)
        return ItemViewHolder(itemView)
    }

    interface OnItemClickListener {
        fun onItemClicked(item: EstablishmentResponse)
    }

    interface OnItemLongClickListener {
        fun onItemLongClicked(item: EstablishmentResponse)
    }


    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: EstablishmentResponse, onItemClickListener: OnItemClickListener, onItemLongClickListener: OnItemLongClickListener) {
            itemView.apply {
                setOnClickListener {
                    onItemClickListener.onItemClicked(item)
                }

                setOnLongClickListener {
                    onItemLongClickListener.onItemLongClicked(item = item)
                    true
                }

                if (item.hasDiscount == true) {
                    mRelative.visibility = View.VISIBLE
                } else {
                    mRelative.visibility = View.GONE
                }

                itemNameTv.text = item.name
                itemAddressTv.text = "${item.address}"
                itemCategoryTv.text = item.categories?.map { it.name  }?.joinToString()
                itemPictureIv.loadImage(item.picture)
                item.distance?.let {
                    itemDistanceTv.visibility = View.VISIBLE
                    itemDistanceTv.text = String.format("%.2f", it) + "km"
                } ?: run {
                    itemDistanceTv.visibility = View.GONE
                }

            }
        }
    }
}
