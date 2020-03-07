package com.sample.dogfetcher.repository

import kotlinx.coroutines.rx2.await

class DogRepository constructor(
    private val dogApi: DogApi
) {

    suspend fun getRandomDogImage(): DogResponse {
        return dogApi.getRandomDogImage().await()
    }

}
