package me.kevincampos.myapplication.challenge1

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class LibraryOneRepository @Inject constructor(
    private val libraryOneApi: LibraryOneApi
) {

    fun getRandomDogImage(onSuccess: (String) -> Unit, onFailure: () -> Unit) {
        libraryOneApi.getRandomDogImage().enqueue(object: Callback<RandomDogResponse> {
            override fun onResponse(
                call: Call<RandomDogResponse>,
                response: Response<RandomDogResponse>
            ) {
                val dogUrl = response.body()!!.message

                onSuccess(dogUrl)
            }

            override fun onFailure(call: Call<RandomDogResponse>, t: Throwable) {
                onFailure()
            }
        })
    }

}