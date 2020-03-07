package com.sample.dogfetcher.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.dogfetcher.usecase.FetchDogUseCase
import com.sample.dogfetcher.utils.Event
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.launch

class DogFetcherViewModel(
    private val fetchDogInfoUseCase: FetchDogUseCase
) : ViewModel() {

    val dogUrl: MutableLiveData<String> = MutableLiveData()
    val dogRecognitionText: MutableLiveData<String> = MutableLiveData()
    val isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val showError = MutableLiveData<Event<String>>()

    private var disposable: Disposable? = null

    fun fetchNewDog() {
        isLoading.postValue(true)

        viewModelScope.launch {
            try {
                val dogInfo = fetchDogInfoUseCase.invoke()
                dogUrl.postValue(dogInfo.dogImageUrl)
                dogRecognitionText.postValue(dogInfo.breedsToText())
                isLoading.postValue(false)
            } catch (throwable: Exception) {
                Log.e("DogFetcherViewModel", "Unable to fetch a dog", throwable)
                isLoading.postValue(false)
                showError.postValue(Event("Unable to fetch a dog"))
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}
