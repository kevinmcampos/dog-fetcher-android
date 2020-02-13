package me.kevincampos.myapplication.challenge1

import javax.inject.Inject

class LibraryOneUseCase @Inject constructor(
    private val libraryOneRepository: LibraryOneRepository
) {

    operator fun invoke(onSuccess: (String) -> Unit, onFailure: () -> Unit) {
        libraryOneRepository.getRandomDogImage(onSuccess, onFailure)
    }


}