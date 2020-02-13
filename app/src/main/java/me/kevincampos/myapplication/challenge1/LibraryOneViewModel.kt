package me.kevincampos.myapplication.challenge1

import androidx.lifecycle.ViewModel
import javax.inject.Inject

class LibraryOneViewModel @Inject constructor(
    private val libraryOneUseCase: LibraryOneUseCase
) : ViewModel() {

    fun fetchNewDog() {
        libraryOneUseCase.invoke({

        }, {

        })
    }

}