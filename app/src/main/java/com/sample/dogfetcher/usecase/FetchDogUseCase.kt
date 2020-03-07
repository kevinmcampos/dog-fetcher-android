package com.sample.dogfetcher.usecase

import com.sample.dogfetcher.model.DogInfo
import com.sample.dogfetcher.repository.DogRepository
import com.sample.dogfetcher.utils.BACKGROUND


class FetchDogUseCase(
    private val classifyDogUseCase: RecogniseDogUseCase,
    private val dogRepository: DogRepository
) {

    suspend fun invokeSuspend(): DogInfo {
        val dogImageUrl = dogRepository.getRandomDogImageSuspend()
        val breeds = classifyDogUseCase.invokeSuspend(dogImageUrl)

        return DogInfo(
            dogImageUrl = dogImageUrl,
            breedRecognitionList = breeds
        )
    }

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
