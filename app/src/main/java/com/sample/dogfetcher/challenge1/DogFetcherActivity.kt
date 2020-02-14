package com.sample.dogfetcher.challenge1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.sample.dogfetcher.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.dog_fetcher_activity.*

class DogFetcherActivity : AppCompatActivity() {

    private lateinit var dogFetcherViewModel: DogFetcherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dog_fetcher_activity)

        dogFetcherViewModel =
            ViewModelProviders.of(this,
                DogViewModelFactory()
            ).get(DogFetcherViewModel::class.java)

        dogFetcherViewModel.dogUrl.observe(this, Observer { dogUrl ->
            Picasso.get()
                .load(dogUrl)
                .noPlaceholder()
                .error()
                .into(dog_image_view)
        })
        dogFetcherViewModel.isLoading.observe(this, Observer { isLoading ->
            loading.isInvisible = !isLoading
        })

        fetch_button.setOnClickListener {
            dogFetcherViewModel.fetchNewDog()
        }
    }

}