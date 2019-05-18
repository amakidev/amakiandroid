package br.com.victorcatao.amaki.ui.adm.contactsList

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.victorcatao.amaki.R
import br.com.victorcatao.amaki.data.remote.models.ContactResponse
import br.com.victorcatao.amaki.ui.base.SimpleBaseRecyclerViewAdapter
import kotlinx.android.synthetic.main.item_contact.view.*

class ContactListAdapter(context: Context, private val onItemClickListener: OnItemClickListener) :
        SimpleBaseRecyclerViewAdapter(context) {

    var mList: List<ContactResponse> = ArrayList()
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
        val itemView = LayoutInflater.from(parent!!.context).inflate(R.layout.item_contact, parent, false)
        return ItemViewHolder(itemView)
    }

    interface OnItemClickListener {
        fun onItemClicked(item: ContactResponse)
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: ContactResponse, onItemClickListener: OnItemClickListener) {
            itemView.apply {
                setOnClickListener {
                    onItemClickListener.onItemClicked(item)
                }

                nameTV.text = item.name
                addressTv.text = item.address
                dateTv.text = item.created_at

            }
        }
    }
}