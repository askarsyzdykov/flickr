package kz.flickr.app.di

import android.content.Context

import kz.flickr.app.di.modules.ApplicationModule
import kz.flickr.app.di.modules.DataModule
import kz.flickr.app.di.modules.OkHttpInterceptorsModule
import kz.flickr.app.domain.repositories.PhotosRepository
import kz.flickr.app.presentation.base.BaseFragment

import javax.inject.Singleton

import dagger.Component

@Singleton
@Component(modules = [(ApplicationModule::class), (DataModule::class), (OkHttpInterceptorsModule::class)])
interface ApplicationComponent {

    fun providePhotosRepository(): PhotosRepository

    fun provideContext(): Context

    fun injectBaseFragment(fragment: BaseFragment)
}