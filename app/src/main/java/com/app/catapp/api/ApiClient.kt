package com.app.catapp.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Koden definerer en klasse kalt ApiClient som oppretter en instans av Retrofit, et tredjeparts bibliotek for å gjøre nettverkskall i Android.
// Klassen har en funksjon som tar inn en boolsk verdi som bestemmer hvilken base-URL som skal brukes for å hente data.
// Klassen har også en companion object som inneholder URL-ene som brukes til å hente data fra serveren.
class ApiClient {
    private var retrofit: Retrofit? = null
    fun client(isFacts: Boolean): Retrofit? {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BASIC
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(if (isFacts) BASE_URL_FACTS else BASE_URL_IMAGES).client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit
    }

    companion object {
        //Endpoints for all requests
        private const val BASE_URL_FACTS = "https://cat-fact.herokuapp.com/"
        private const val BASE_URL_IMAGES = "https://api.thecatapi.com/v1/"
    }
}