package com.app.catapp.api.cat_facts

import com.app.catapp.model.CatFact
import retrofit2.Call
import retrofit2.http.GET

interface CatFactsApi {
    @GET("facts/random?animal_type=cat&amount=50")
    fun getFacts(): Call<List<CatFact>>
}