package kz.flickr.app.di.modules

import kz.flickr.app.di.qualifiers.OkHttpInterceptors
import kz.flickr.app.di.qualifiers.OkHttpNetworkInterceptors

import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import okhttp3.Interceptor

import java.util.Collections.emptyList

@Module
class OkHttpInterceptorsModule {

    @Provides
    @OkHttpInterceptors
    @Singleton
    @NonNull
    fun provideOkHttpInterceptors(): List<Interceptor> {
        return emptyList()
    }

    @Provides
    @OkHttpNetworkInterceptors
    @Singleton
    @NonNull
    fun provideOkHttpNetworkInterceptors(): List<Interceptor> {
        return emptyList()
    }

}
