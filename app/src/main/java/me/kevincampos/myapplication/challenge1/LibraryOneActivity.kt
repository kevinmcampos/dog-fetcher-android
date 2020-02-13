package me.kevincampos.myapplication.challenge1

import android.os.Bundle
import android.view.View
import dagger.android.DaggerActivity
import me.kevincampos.myapplication.R
import javax.inject.Inject

class LibraryOneActivity : DaggerActivity() {

    @Inject
    lateinit var libraryOneViewModel: LibraryOneViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.display_dog_activity)
    }

    fun fetchNewDog(view: View) {
        libraryOneViewModel.fetchNewDog()
    }

}