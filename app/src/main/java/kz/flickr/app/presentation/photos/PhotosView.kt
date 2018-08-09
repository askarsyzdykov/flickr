package kz.flickr.app.presentation.photos

import com.arellomobile.mvp.MvpView
import kz.flickr.app.data.network.models.Photo

interface PhotosView : MvpView {

    fun showProgressDialog()

    fun hideProgressDialog()

    fun onSuccess(photos: List<Photo>)

    fun onLoadMoreSucceeded(photos: List<Photo>)

    fun onError(message: String)

    fun showPhotoScreen(url: String)
}