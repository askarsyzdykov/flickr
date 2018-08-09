package kz.flickr.app.di.modules

import android.content.Context

import kz.flickr.app.system.ResourceManager

import javax.inject.Singleton

import dagger.Module
import dagger.Provides

@Module
class ApplicationModule(context: Context) {

    private val context: Context = context.applicationContext

    @Provides
    @Singleton
    internal fun provideApplicationContext(): Context {
        return context
    }

    @Provides
    @Singleton
    internal fun provideResourceManager(context: Context): ResourceManager {
        return ResourceManager(context)
    }

}
