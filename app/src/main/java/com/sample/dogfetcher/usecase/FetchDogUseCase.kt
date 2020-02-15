package com.sample.dogfetcher.usecase

import com.sample.dogfetcher.model.DogInfo
import com.sample.dogfetcher.repository.DogRepository
import com.sample.dogfetcher.utils.BACKGROUND


class FetchDogUseCase(
    private val classifyDogUseCase: RecogniseDogUseCase,
    private val dogRepository: DogRepository
) {

    operator fun invoke(onCompleted: (DogInfo) -> Unit, onError: (Throwable) -> Unit) {
        dogRepository.getRandomDogImage({ dogImageUrl ->
            BACKGROUND.submit {
                val breeds = classifyDogUseCase.invoke(dogImageUrl)

                onCompleted(
                    DogInfo(
                        dogImageUrl = dogImageUrl,
                        breedRecognitionList = breeds
                    )
                )
            }
        }, onError)
    }

}
