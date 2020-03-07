package com.sample.dogfetcher.usecase

import com.sample.dogfetcher.model.DogInfo
import com.sample.dogfetcher.repository.DogRepository
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.rx2.await
import kotlinx.coroutines.withContext


class FetchDogUseCase(
    private val classifyDogUseCase: RecogniseDogUseCase,
    private val dogRepository: DogRepository
) {

    suspend operator fun invoke(): DogInfo {
        // Another option to change threads using Rx Scheduler
        // val dogResponse = dogRepository.getRandomDogImage().subscribeOn(Schedulers.io()).await()

        val dogResponse = withContext(Dispatchers.IO) {
            dogRepository.getRandomDogImage().subscribeOn(Schedulers.io()).await()
        }

        val dogImageUrl = dogResponse.message
        val breeds = withContext(Dispatchers.IO) {
            classifyDogUseCase.invoke(dogImageUrl)
        }
        return DogInfo(dogImageUrl = dogImageUrl, breedRecognitionList = breeds)
    }

}
