package com.sample.dogfetcher.challenge1

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DogFetcherViewModel(
    private val libraryOneUseCase: FetchDogUseCase
) : ViewModel() {

    val dogUrl: MutableLiveData<String> = MutableLiveData()
    val isLoading: MutableLiveData<Boolean> = MutableLiveData(false)

    fun fetchNewDog() {
        isLoading.postValue(true)
        libraryOneUseCase.invoke({
            dogUrl.postValue(it)
            isLoading.postValue(false)
        }, {
            Log.e(":(", "failed to load dog")
            isLoading.postValue(false)
        })
    }

}