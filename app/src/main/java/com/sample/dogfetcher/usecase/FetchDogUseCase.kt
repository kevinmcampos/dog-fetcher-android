package com.sample.dogfetcher.usecase

import com.sample.dogfetcher.model.DogInfo
import com.sample.dogfetcher.repository.DogRepository
import kotlinx.coroutines.rx2.await


class FetchDogUseCase(
    private val classifyDogUseCase: RecogniseDogUseCase,
    private val dogRepository: DogRepository
) {

    suspend operator fun invoke(): DogInfo {
        val dogResponse = dogRepository.getRandomDogImage().await()
        val dogImageUrl = dogResponse.message
        val breeds = classifyDogUseCase.invoke(dogImageUrl)
        return DogInfo(dogImageUrl = dogImageUrl, breedRecognitionList = breeds)
    }

}
