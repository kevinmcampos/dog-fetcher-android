package com.sample.dogfetcher

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.sample.dogfetcher.viewmodel.DogFetcherViewModel
import com.sample.dogfetcher.viewmodel.DogViewModelFactory
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.dog_fetcher_activity.*

class DogFetcherActivity : AppCompatActivity() {

    private lateinit var viewModel: DogFetcherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dog_fetcher_activity)

        viewModel =
            ViewModelProviders.of(this,
                DogViewModelFactory(
                    baseContext
                )
            ).get(DogFetcherViewModel::class.java)

        viewModel.dogUrl.observe(this, Observer { dogUrl ->
            Picasso.get()
                .load(dogUrl)
                .noPlaceholder()
                .into(dog_image_view)
        })
        viewModel.dogRecognitionText.observe(this, Observer {
            breeds_list_text.text = it
        })
        viewModel.isLoading.observe(this, Observer { isLoading ->
            loading.isInvisible = !isLoading
        })

        fetch_button.setOnClickListener {
            viewModel.fetchNewDog()
        }
    }

}