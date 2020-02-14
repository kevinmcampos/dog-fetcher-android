package com.sample.dogfetcher.challenge1

import com.sample.dogfetcher.challenge1.repository.DogRepository

class FetchDogUseCase(
    private val dogRepository: DogRepository
) {

    operator fun invoke(onSuccess: (String) -> Unit, onFailure: () -> Unit) {
        dogRepository.getRandomDogImage(onSuccess, onFailure)
    }

}
