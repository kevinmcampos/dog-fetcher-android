package com.sample.dogfetcher.challenge1.repository

import retrofit2.Call
import retrofit2.http.GET

interface DogApi {

    @GET("/api/breeds/image/random")
    fun getRandomDogImage(): Call<DogResponse>

}