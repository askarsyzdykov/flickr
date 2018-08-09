package kz.flickr.app.data.network.interceptors


import kz.flickr.app.data.network.NetworkChecker
import kz.flickr.app.domain.exceptions.NoNetworkException
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class NetworkCheckInterceptor(private val networkChecker: NetworkChecker) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        if (!networkChecker.isConnected) {
            throw NoNetworkException("No internet connection")
        }

        try {
            return chain.proceed(requestBuilder.build())
        } catch (e: SocketTimeoutException) {
            throw NoNetworkException()
        } catch (e: UnknownHostException) {
            throw NoNetworkException()
        }

    }

}
