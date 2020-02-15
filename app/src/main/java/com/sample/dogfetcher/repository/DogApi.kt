package com.sample.dogfetcher.repository

import retrofit2.Call
import retrofit2.http.GET

interface DogApi {

    @GET("/api/breeds/image/random")
    fun getRandomDogImage(): Call<DogResponse>

}