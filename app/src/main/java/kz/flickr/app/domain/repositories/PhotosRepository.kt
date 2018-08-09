package kz.flickr.app.domain.repositories

import io.reactivex.Single
import kz.flickr.app.data.network.models.PhotosWrapper

interface PhotosRepository {

    fun loadPhotos(page: Int, text: String?): Single<PhotosWrapper>

}