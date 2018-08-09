package kz.flickr.app.presentation.photos

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import kz.flickr.app.data.network.models.Photo
import kz.flickr.app.data.network.models.PhotosWrapper
import kz.flickr.app.domain.exceptions.ExceptionFactory
import kz.flickr.app.domain.interactors.PhotosInteractor
import kz.flickr.app.presentation.common.RxSchedulersProvider
import kz.flickr.app.presentation.utils.ErrorMessageFactory
import kz.flickr.app.system.ResourceManager
import javax.inject.Inject

/**
 * Created by Askar Syzdykov on 8/7/18.
 */
@InjectViewState
class PhotosPresenter @Inject constructor(private val photosInteractor: PhotosInteractor,
                                          private val rxSchedulersProvider: RxSchedulersProvider,
                                          private val resourceManager: ResourceManager)
    : MvpPresenter<PhotosView>() {

    private var text: String? = null
    private var page = 1
    private var totalPages = Int.MAX_VALUE
    lateinit var photos: MutableList<Photo>

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadPhotos(page, text)
    }

    fun loadMore() {
        if (page < totalPages) {
            loadPhotos(++page, text)
        }
    }

    fun onPhotoClick(photo: Photo) {
        viewState.showPhotoScreen(photo.getUrl())
    }

    fun search(query: String?) {
        page = 1
        loadPhotos(page, query)
    }

    private fun loadPhotos(page: Int, text: String?) {
        this.text = text
        photosInteractor.loadPhotos(page, text)
                .subscribeOn(rxSchedulersProvider.ioScheduler)
                .observeOn(rxSchedulersProvider.mainThreadScheduler)
                .doOnSubscribe { viewState.showProgressDialog() }
                .doFinally { viewState.hideProgressDialog() }
                .subscribe(this::onSuccess, this::onError)
    }

    private fun onSuccess(photosWrapper: PhotosWrapper) {
        page = photosWrapper.page
        totalPages = photosWrapper.pages
        if (page == 1) {
            photos = photosWrapper.photo.toMutableList()
            viewState.onSuccess(photos)
        } else {
            photos.addAll(photosWrapper.photo)
            viewState.onLoadMoreSucceeded(photos)
        }
    }

    private fun onError(throwable: Throwable) {
        viewState.onError(ErrorMessageFactory.create(resourceManager, ExceptionFactory.getException(throwable)))
    }

}