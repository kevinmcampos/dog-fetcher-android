package com.sample.dogfetcher.model

data class DogInfo(
    val dogImageUrl: String,
    val breedRecognitionList: List<BreedRecognition>
) {

    fun breedsToText(): String {
        return breedRecognitionList.joinToString(separator = "\n") { breedRecognition ->
            "${breedRecognition.breed} (${(breedRecognition.confidence * 100).toInt()}%)"
        }
    }

}
