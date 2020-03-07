package com.sample.dogfetcher.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.dogfetcher.usecase.FetchDogUseCase
import com.sample.dogfetcher.utils.Event
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.rx2.await
import kotlinx.coroutines.withContext

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
                // Another option
                // val dogInfo = fetchDogInfoUseCase().subscribeOn(Schedulers.io()).await()

                val dogInfo = withContext(Dispatchers.IO) {
                    fetchDogInfoUseCase().await()
                }
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

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}
