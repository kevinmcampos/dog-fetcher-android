package com.sample.dogfetcher.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sample.dogfetcher.usecase.FetchDogUseCase
import com.sample.dogfetcher.utils.Event
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.rx2.rxSingle

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

        disposable = rxSingle { fetchDogInfoUseCase() }
            .subscribeOn(Schedulers.io())
            .subscribe({ dogInfo ->
                dogUrl.postValue(dogInfo.dogImageUrl)
                dogRecognitionText.postValue(dogInfo.breedsToText())
                isLoading.postValue(false)
            }, { throwable ->
                Log.e("DogFetcherViewModel", "Unable to fetch a dog", throwable)
                isLoading.postValue(false)
                showError.postValue(Event("Unable to fetch a dog"))
            })
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}
