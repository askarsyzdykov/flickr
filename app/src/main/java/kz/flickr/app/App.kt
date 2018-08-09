package kz.flickr.app

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import kz.flickr.app.di.ApplicationComponent
import kz.flickr.app.di.DaggerApplicationComponent
import kz.flickr.app.di.modules.ApplicationModule
import kz.flickr.app.di.modules.DataModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        Fresco.initialize(this)

        component = buildComponent()
    }

    fun buildComponent(): ApplicationComponent {
        return DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .dataModule(DataModule(BuildConfig.HOST))
                .build()
    }

    companion object {
        var component: ApplicationComponent? = null
            private set

    }
}
