package com.sample.dogfetcher.repository

import io.reactivex.Single
import kotlinx.coroutines.rx2.rxSingle

class DogRepository constructor(
    private val dogApi: DogApi
) {

    fun getRandomDogImage(): Single<DogResponse> {
        return rxSingle {
            dogApi.getRandomDogImage()
        }
    }

}
