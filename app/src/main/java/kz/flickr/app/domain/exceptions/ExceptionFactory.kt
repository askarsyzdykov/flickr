package kz.flickr.app.domain.exceptions

import java.net.ConnectException
import java.net.UnknownHostException

/**
 * Created by Askar on 12/9/17.
 */

object ExceptionFactory {

    fun getException(exception: Throwable): Throwable {
        return when (exception) {
            is UnknownHostException, is ConnectException -> NoNetworkException(exception)
            is retrofit2.HttpException -> HttpException(exception)
            else -> exception
        }
    }

}
