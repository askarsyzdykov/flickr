package kz.flickr.app.presentation.utils

import kz.flickr.app.R
import kz.flickr.app.domain.exceptions.HttpException
import kz.flickr.app.domain.exceptions.NoNetworkException
import kz.flickr.app.system.ResourceManager

import java.net.HttpURLConnection

object ErrorMessageFactory {

    fun create(resourceManager: ResourceManager, exception: Throwable): String {
        return when (exception) {
            is NoNetworkException -> resourceManager.getString(R.string.error_no_internet_connection)
            else -> resourceManager.getString(R.string.error_default_exception)
        }
    }

}
