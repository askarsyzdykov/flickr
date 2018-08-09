package kz.flickr.app.data

import kz.flickr.app.data.network.RestApiService
import kz.flickr.app.data.network.models.PhotosWrapper
import kz.flickr.app.domain.repositories.PhotosRepository
import io.reactivex.Single
import javax.inject.Inject

class PhotosRepositoryImpl @Inject constructor(private val apiService: RestApiService) : PhotosRepository {

    override fun loadPhotos(page: Int, text: String?): Single<PhotosWrapper> {
        val method = if (text != null) "flickr.photos.search" else "flickr.photos.getRecent"
        return apiService
                .getPhotos(method, page, text)
                .map { it.photos }
    }

}