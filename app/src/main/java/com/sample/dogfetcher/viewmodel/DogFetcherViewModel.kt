package com.sample.dogfetcher.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sample.dogfetcher.usecase.FetchDogUseCase

class DogFetcherViewModel(
    private val fetchDogUseCase: FetchDogUseCase
) : ViewModel() {

    val dogUrl: MutableLiveData<String> = MutableLiveData()
    val dogRecognitionText: MutableLiveData<String> = MutableLiveData()
    val isLoading: MutableLiveData<Boolean> = MutableLiveData(false)

    fun fetchNewDog() {
        isLoading.postValue(true)

        fetchDogUseCase.invoke(
            onCompleted = { dogInfo ->
                dogUrl.postValue(dogInfo.dogImageUrl)
                dogRecognitionText.postValue(
                    dogInfo.breedRecognitionList
                        .map { breedRecognition ->
                            "${breedRecognition.breed} (${(breedRecognition.confidence * 100).toInt()}%)"
                        }
                        .joinToString(separator = "\n")
                )
                isLoading.postValue(false)
            },
            onError = {
                Log.e(":(", "failed to load dog")
                isLoading.postValue(false)
            }
        )
    }

}
