package br.com.victorcatao.amaki.ui.adm.categoriesList

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.victorcatao.amaki.R
import br.com.victorcatao.amaki.data.remote.models.CategoryResponse
import br.com.victorcatao.amaki.data.remote.models.EstablishmentResponse
import br.com.victorcatao.amaki.ui.base.SimpleBaseRecyclerViewAdapter
import br.com.victorcatao.amaki.ui.home.EstablishmentListAdapter
import kotlinx.android.synthetic.main.item_list.view.*

class CategoriesListAdapter(context: Context, private val onItemClickListener: OnItemClickListener, private val onItemLongClickListener: CategoriesListAdapter.OnItemLongClickListener) :
        SimpleBaseRecyclerViewAdapter(context) {

    var mList: List<CategoryResponse> = ArrayList()
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
        val itemView = LayoutInflater.from(parent!!.context).inflate(R.layout.item_list, parent, false)
        return ItemViewHolder(itemView)
    }

    interface OnItemClickListener {
        fun onItemClicked(item: CategoryResponse)
    }


    interface OnItemLongClickListener {
        fun onItemLongClicked(item: CategoryResponse)
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: CategoryResponse, onItemClickListener: OnItemClickListener, onItemLongClickListener: OnItemLongClickListener) {
            itemView.apply {
                setOnClickListener {
                    onItemClickListener.onItemClicked(item)
                }

                setOnLongClickListener {
                    onItemLongClickListener.onItemLongClicked(item = item)
                    true
                }

                itemListContenTv.text = item.name

            }
        }
    }
}
