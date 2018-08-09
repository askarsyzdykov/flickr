package kz.flickr.app.domain.interactors

import kz.flickr.app.data.network.models.PhotosWrapper
import kz.flickr.app.domain.repositories.PhotosRepository
import io.reactivex.Single
import javax.inject.Inject

class PhotosInteractor @Inject constructor(private val loginRepository: PhotosRepository) {

    fun loadPhotos(page: Int, text: String?): Single<PhotosWrapper> {
        return loginRepository.loadPhotos(page, text)
    }

}