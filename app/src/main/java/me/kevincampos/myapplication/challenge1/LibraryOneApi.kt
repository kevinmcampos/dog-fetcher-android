package me.kevincampos.myapplication.challenge1

import retrofit2.Call
import retrofit2.http.GET

interface LibraryOneApi {

    @GET("/api/breeds/image/random")
    fun getRandomDogImage(): Call<RandomDogResponse>

}