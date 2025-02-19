package com.sample.dogfetcher.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sample.dogfetcher.usecase.FetchDogUseCase
import com.sample.dogfetcher.utils.Event

class DogFetcherViewModel(
    private val fetchDogInfoUseCase: FetchDogUseCase
) : ViewModel() {

    val dogUrl: MutableLiveData<String> = MutableLiveData()
    val dogRecognitionText: MutableLiveData<String> = MutableLiveData()
    val isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val showError = MutableLiveData<Event<String>>()

    fun fetchNewDog() {
        isLoading.postValue(true)

        fetchDogInfoUseCase.invoke({ dogInfo ->
                dogUrl.postValue(dogInfo.dogImageUrl)
                dogRecognitionText.postValue(dogInfo.breedsToText())
                isLoading.postValue(false)
            }, { throwable ->
                Log.e("DogFetcherViewModel", "Unable to fetch a dog", throwable)
                isLoading.postValue(false)
                showError.postValue(Event("Unable to fetch a dog"))
            }
        )
    }

}
