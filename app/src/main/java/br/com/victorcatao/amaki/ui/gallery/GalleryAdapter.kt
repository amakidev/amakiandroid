package br.com.victorcatao.amaki.ui.gallery

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.victorcatao.amaki.R
import br.com.victorcatao.amaki.data.remote.models.Picture
import br.com.victorcatao.amaki.ui.base.SimpleBaseRecyclerViewAdapter
import br.com.victorcatao.amaki.utils.extensions.loadImage
import br.com.victorcatao.amaki.utils.extensions.setVisible
import kotlinx.android.synthetic.main.item_gallery.view.*

class GalleryAdapter(context: Context, private val onItemClickListener: OnItemClickListener) : SimpleBaseRecyclerViewAdapter(context){

    
    var list = mutableListOf<Picture>()
        
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    
    
    override fun getDisplayableItemsCount() = list.size

    override fun onBindRecyclerViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        
        if (holder is GalleryAdapter.ItemViewHolder)
            holder.bind(list[position], position)
    }

    override fun getItemViewHolder(parent: ViewGroup?): RecyclerView.ViewHolder {
        
        val itemView = LayoutInflater.from(parent?.context).inflate(R.layout.item_gallery, parent, false)
        return ItemViewHolder(itemView)
    }
    
    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(item: Picture, position: Int){
            itemView.apply {

                photoIv.loadImage(item.url)

                if(item.loaded) {
                    photoIv.setOnClickListener {
                        onItemClickListener.onItemClicked(item)
                    }

                    photoRemoveIv.setOnClickListener {
                        if(item.error){
                            removePicture(item)
                        } else{
                            onRemoveLoading(position)
                            onItemClickListener.onRemoveItemClicked(item)
                        }
                    }

                    photoRemoveIv.setVisible(!item.error)
                }

                photoRetryFl.setOnClickListener{
                    list.removeAt(position)
                    notifyDataSetChanged()
                    onItemClickListener.onRetryImageClicked(item)
                }

                photoRetryFl.setVisible(item.error)
                photoProgressFl.setVisible(!item.loaded)

            }
        }
        
    }

    fun addLoadingImage(photo: Picture) {
        photo.loaded = false
        list.add(photo)
        notifyDataSetChanged()
    }

    fun onPictureError() {
        list.last().apply {
            loaded = true
            error = true
        }

        notifyDataSetChanged()
    }

    fun onRemoveError(photo: Picture) {
        val index = list.indexOf(photo)
        list[index].apply {
            loaded = true
            error = false
        }

        notifyDataSetChanged()
    }

    fun addPicture(photo: Picture) {
        list.last().apply {
            loaded = true
            id = photo.id
            url = photo.url
        }

        notifyDataSetChanged()
    }

    private fun onRemoveLoading(position: Int) {
        list[position].apply {
            loaded = false
        }

        notifyDataSetChanged()
    }

    fun removePicture(photo: Picture) {
        list.remove(photo)
        notifyDataSetChanged()
    }


    interface OnItemClickListener {
        fun onItemClicked(item: Picture)
        fun onRemoveItemClicked(item: Picture)
        fun onRetryImageClicked(item: Picture)
    }
}