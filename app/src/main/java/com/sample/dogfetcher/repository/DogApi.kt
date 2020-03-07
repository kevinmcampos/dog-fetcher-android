package com.sample.dogfetcher.repository

import retrofit2.http.GET

interface DogApi {

    @GET("/api/breeds/image/random")
    suspend fun getRandomDogImage(): DogResponse

}