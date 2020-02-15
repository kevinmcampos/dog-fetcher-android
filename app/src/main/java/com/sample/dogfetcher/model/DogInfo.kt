package com.sample.dogfetcher.model

data class DogInfo(
    val dogImageUrl: String,
    val breedRecognitionList: List<BreedRecognition>
)
