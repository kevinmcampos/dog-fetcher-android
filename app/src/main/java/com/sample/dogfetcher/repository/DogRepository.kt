package com.sample.dogfetcher.repository

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class DogRepository constructor(
    private val dogApi: DogApi
) {

    suspend fun getRandomDogImageSuspend(): String {
        return dogApi.getRandomDogImageSuspend().message
    }

    fun getRandomDogImage(onCompleted: (String) -> Unit, onError: (Throwable) -> Unit) {
        dogApi.getRandomDogImage().enqueue(object : Callback<DogResponse> {
            override fun onResponse(
                call: Call<DogResponse>,
                response: Response<DogResponse>
            ) {
                if (response.isSuccessful) {
                    val dogResponse = requireNotNull(response.body())
                    onCompleted(dogResponse.message)
                } else {
                    onError(IOException("${response.errorBody()}"))
                }
            }

            override fun onFailure(call: Call<DogResponse>, t: Throwable) {
                onError(t)
            }
        })
    }

}
