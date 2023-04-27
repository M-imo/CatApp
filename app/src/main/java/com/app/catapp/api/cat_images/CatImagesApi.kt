package com.app.catapp.api.cat_images

import com.app.catapp.model.CatImage
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface CatImagesApi {

    @GET("images/search")
    fun getImages(@Header("x-api-key") apiKey: String = "live_PCuhxlyyWOV62oeF24IPXuCvGg1JXVsMSQbBcsZB5ohVcMcF6vjojsFc9GdFVuF6"): Call<List<CatImage>>
}