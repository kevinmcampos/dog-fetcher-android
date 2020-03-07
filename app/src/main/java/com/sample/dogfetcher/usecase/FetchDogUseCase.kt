package com.sample.dogfetcher.usecase

import com.sample.dogfetcher.model.DogInfo
import com.sample.dogfetcher.repository.DogRepository
import io.reactivex.Single


class FetchDogUseCase(
    private val classifyDogUseCase: RecogniseDogUseCase,
    private val dogRepository: DogRepository
) {

    operator fun invoke(): Single<DogInfo> {
        return dogRepository.getRandomDogImage()
            .flatMap { dogResponse ->
                val dogImageUrl = dogResponse.message
                val breeds = classifyDogUseCase.invoke(dogImageUrl)

                Single.just(DogInfo(dogImageUrl = dogImageUrl, breedRecognitionList = breeds))
            }

    }

}
