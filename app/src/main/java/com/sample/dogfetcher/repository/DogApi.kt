package com.sample.dogfetcher.repository

import io.reactivex.Single
import retrofit2.http.GET

interface DogApi {

    @GET("/api/breeds/image/random")
    fun getRandomDogImage(): Single<DogResponse>

}