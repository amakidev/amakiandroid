package br.com.victorcatao.amaki.ui.search

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.victorcatao.amaki.R
import br.com.victorcatao.amaki.data.remote.models.SearchModel
import br.com.victorcatao.amaki.ui.base.SimpleBaseRecyclerViewAdapter
import kotlinx.android.synthetic.main.item_search.view.*

class SearchListAdapter(context: Context, private val onItemClickListener: OnItemClickListener) :
        SimpleBaseRecyclerViewAdapter(context) {

    var mList: List<SearchModel> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getDisplayableItemsCount() = mList.size

    override fun onBindRecyclerViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            holder.bind(mList[position], onItemClickListener)
        }
    }

    override fun getItemViewHolder(parent: ViewGroup?): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent!!.context).inflate(R.layout.item_search, parent, false)
        return ItemViewHolder(itemView)
    }

    interface OnItemClickListener {
        fun onItemClicked(item: SearchModel)
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: SearchModel, onItemClickListener: OnItemClickListener) {
            itemView.apply {
                setOnClickListener {
                    onItemClickListener.onItemClicked(item)
                }
                itemRadioBtn.text = item.name
                itemRadioBtn.isChecked = item.isChecked
//                categoryNameTv.text = item.name
            }
        }
    }
}