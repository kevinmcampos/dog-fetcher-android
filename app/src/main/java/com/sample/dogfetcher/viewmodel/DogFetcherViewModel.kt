package com.sample.dogfetcher.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.dogfetcher.repository.DogApi
import com.sample.dogfetcher.usecase.FetchDogUseCase
import com.sample.dogfetcher.utils.Event
import kotlinx.coroutines.launch

class DogFetcherViewModel(
    private val fetchDogInfoUseCase: FetchDogUseCase,
    private val dogApi: DogApi // Not necessary, just for demonstration proposes
) : ViewModel() {

    val dogUrl: MutableLiveData<String> = MutableLiveData()
    val dogRecognitionText: MutableLiveData<String> = MutableLiveData()
    val isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val showError = MutableLiveData<Event<String>>()

    fun fetchNewDog() {
        viewModelScope.launch {
            try {
                isLoading.postValue(true)
                val dogInfo = fetchDogInfoUseCase.invokeSuspend()
                dogUrl.postValue(dogInfo.dogImageUrl)
                dogRecognitionText.postValue(dogInfo.breedsToText())
                isLoading.postValue(false)
            } catch (e: Exception) {
                Log.e("DogFetcherViewModel", "Unable to fetch a dog", e)
                isLoading.postValue(false)
                showError.postValue(Event("Unable to fetch a dog"))
            }

        }

    }

    fun fetchNewDogCallback() {
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
