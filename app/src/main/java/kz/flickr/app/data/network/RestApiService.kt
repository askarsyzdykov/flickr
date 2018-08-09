package kz.flickr.app.data.network

import kz.flickr.app.data.network.models.ResponseWrapper

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RestApiService {

    @GET("/services/rest?format=json&nojsoncallback=?")
    fun getPhotos(@Query("method") method: String,
                  @Query("page") page: Int,
                  @Query("text") text: String?): Single<ResponseWrapper>

}
