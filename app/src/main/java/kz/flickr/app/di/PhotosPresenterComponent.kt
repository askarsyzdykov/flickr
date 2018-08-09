package kz.flickr.app.di

import kz.flickr.app.di.scopes.Presenter
import kz.flickr.app.presentation.photos.PhotosPresenter
import dagger.Component

@Presenter
@Component(dependencies = [ApplicationComponent::class])
interface PhotosPresenterComponent {

    fun getPhotosPresenter(): PhotosPresenter

}