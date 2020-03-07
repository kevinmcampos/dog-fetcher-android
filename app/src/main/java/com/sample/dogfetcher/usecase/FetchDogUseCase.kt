package com.sample.dogfetcher.usecase

import com.sample.dogfetcher.model.DogInfo
import com.sample.dogfetcher.repository.DogRepository
import io.reactivex.Single
import kotlinx.coroutines.rx2.await
import kotlinx.coroutines.rx2.rxSingle


class FetchDogUseCase(
    private val classifyDogUseCase: RecogniseDogUseCase,
    private val dogRepository: DogRepository
) {

    operator fun invoke(): Single<DogInfo> {
        return rxSingle {
            val dogResponse = dogRepository.getRandomDogImage().await()
            val dogImageUrl = dogResponse.message
            val breeds = classifyDogUseCase.invoke(dogImageUrl)

            DogInfo(dogImageUrl = dogImageUrl, breedRecognitionList = breeds)
        }
    }

}
