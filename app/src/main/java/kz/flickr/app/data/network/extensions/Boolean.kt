package kz.flickr.app.data.remote.extensions

/**
 * Created by askar on 1/30/18.
 */

fun Boolean.toInt(): Int {
    return if (this) 1 else 0
}