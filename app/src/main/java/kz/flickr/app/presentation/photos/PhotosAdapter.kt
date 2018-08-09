package kz.flickr.app.presentation.photos

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.facebook.drawee.view.SimpleDraweeView
import kz.flickr.app.R
import kz.flickr.app.data.network.models.Photo

class PhotosAdapter(private val items: List<Photo>, private val listener: PhotosAdapterClickListener?)
    : RecyclerView.Adapter<PhotosAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.photo_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val photo = items[position]

        holder.imgPhoto.setImageURI(photo.getUrl())
        holder.itemView.setOnClickListener { _ ->
            listener?.onPhotoClick(photo)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imgPhoto: SimpleDraweeView

        init {
            imgPhoto = itemView.findViewById(R.id.imgPhoto)
        }
    }

    interface PhotosAdapterClickListener {
        fun onPhotoClick(photo: Photo)
    }
}