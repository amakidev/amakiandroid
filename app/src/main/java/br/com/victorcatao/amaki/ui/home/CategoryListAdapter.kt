package br.com.victorcatao.amaki.ui.home

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.victorcatao.amaki.R
import br.com.victorcatao.amaki.data.remote.models.CategoryResponse
import br.com.victorcatao.amaki.ui.base.SimpleBaseRecyclerViewAdapter
import kotlinx.android.synthetic.main.item_category.view.*
import org.jetbrains.anko.textColor

class CategoryListAdapter(context: Context, private val onItemClickListener: OnItemClickListener) :
        SimpleBaseRecyclerViewAdapter(context) {

    var mList: List<CategoryResponse> = ArrayList()
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
        //TODO change layout to inflate
        val itemView = LayoutInflater.from(parent!!.context).inflate(R.layout.item_category, parent, false)
        return ItemViewHolder(itemView)
    }

    interface OnItemClickListener {
        fun onItemClicked(item: CategoryResponse)
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: CategoryResponse, onItemClickListener: OnItemClickListener) {
            itemView.apply {
                //TODO bind view
                setOnClickListener {
                    onItemClickListener.onItemClicked(item)
                }
                categoryNameTv.text = item.name?.toUpperCase()
                categoryNameTv.textColor = if(item.selected == true) resources.getColor(R.color.color_black) else resources.getColor(R.color.color_white)
            }
        }
    }
}
