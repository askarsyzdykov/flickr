package kz.flickr.app.data.network.models

/**
 * Created by Askar Syzdykov on 8/7/18.
 */
class Photo constructor(val id: String,
                        val owner: String,
                        val secret: String,
                        val server: String,
                        val farm: Int,
                        val ispublic: Int,
                        val isfriend: Int,
                        val isfamily: Int) {

    fun getUrl(): String {
        return "https://farm$farm.staticflickr.com/$server/${id}_$secret.jpg"
    }
}