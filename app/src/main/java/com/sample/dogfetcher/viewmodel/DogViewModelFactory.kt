package com.sample.dogfetcher.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sample.dogfetcher.repository.DogApi
import com.sample.dogfetcher.repository.DogRepository
import com.sample.dogfetcher.repository.ThrottleNetworkSpeedInterceptor
import com.sample.dogfetcher.usecase.FetchDogUseCase
import com.sample.dogfetcher.usecase.RecogniseDogUseCase
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DogViewModelFactory(context: Context) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass != DogFetcherViewModel::class.java) {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
        return DogFetcherViewModel(
            fetchDogUseCase
        ) as T
    }

    private val fetchDogUseCase: FetchDogUseCase by lazy {
        FetchDogUseCase(
            recogniseDogUseCase,
            dogRepository
        )
    }

    private val recogniseDogUseCase: RecogniseDogUseCase by lazy {
        RecogniseDogUseCase(context)
    }

    private val dogRepository: DogRepository by lazy {
        DogRepository(dogApi)
    }

    private val dogApi: DogApi by lazy {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(ThrottleNetworkSpeedInterceptor())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://dog.ceo/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(DogApi::class.java)
    }

}