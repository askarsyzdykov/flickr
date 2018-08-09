package kz.flickr.app.data.network.models

class ResponseWrapper constructor(val photos: PhotosWrapper, val stat: String)

class PhotosWrapper constructor(val photo: List<Photo>,
                                val page: Int,
                                val pages: Int,
                                val perpage: Int,
                                val total: Int)