package kz.flickr.app.di.modules

import com.facebook.stetho.okhttp3.StethoInterceptor
import dagger.Module
import dagger.Provides
import kz.flickr.app.di.qualifiers.OkHttpInterceptors
import kz.flickr.app.di.qualifiers.OkHttpNetworkInterceptors
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
class OkHttpInterceptorsModule {

    @Provides
    @OkHttpInterceptors
    @Singleton
    fun provideOkHttpInterceptors(): List<Interceptor> {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return listOf<Interceptor>(httpLoggingInterceptor)
    }

    @Provides
    @OkHttpNetworkInterceptors
    @Singleton
    fun provideOkHttpNetworkInterceptors(): List<Interceptor> {
        return listOf<Interceptor>(StethoInterceptor())
    }

}
