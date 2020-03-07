package com.sample.dogfetcher.repository

import io.reactivex.Single

class DogRepository constructor(
    private val dogApi: DogApi
) {

    fun getRandomDogImage(): Single<DogResponse> {
        return dogApi.getRandomDogImage()
    }

}
