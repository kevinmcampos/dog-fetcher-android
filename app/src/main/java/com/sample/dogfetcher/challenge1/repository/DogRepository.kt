package com.sample.dogfetcher.challenge1.repository

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DogRepository constructor(
    private val dogApi: DogApi
) {

    fun getRandomDogImage(onSuccess: (String) -> Unit, onFailure: () -> Unit) {
        dogApi.getRandomDogImage().enqueue(object: Callback<DogResponse> {
            override fun onResponse(
                call: Call<DogResponse>,
                response: Response<DogResponse>
            ) {
                val dogUrl = response.body()!!.message

                onSuccess(dogUrl)
            }

            override fun onFailure(call: Call<DogResponse>, t: Throwable) {
                onFailure()
            }
        })
    }

}