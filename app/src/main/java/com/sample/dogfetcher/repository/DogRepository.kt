package com.sample.dogfetcher.repository

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DogRepository constructor(
    private val dogApi: DogApi
) {

    fun getRandomDogImage(onCompleted: (String) -> Unit, onError: (Throwable) -> Unit) {
        dogApi.getRandomDogImage().enqueue(object : Callback<DogResponse> {
            override fun onResponse(
                call: Call<DogResponse>,
                response: Response<DogResponse>
            ) {
                val dogUrl = response.body()!!.message

                onCompleted(dogUrl)
            }

            override fun onFailure(call: Call<DogResponse>, t: Throwable) {
                onError(t)
            }
        })
    }

}
