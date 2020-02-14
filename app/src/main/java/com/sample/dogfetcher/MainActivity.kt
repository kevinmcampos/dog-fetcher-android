package com.sample.dogfetcher

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sample.dogfetcher.challenge1.DogFetcherActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun openChallengeOne(view: View) {
        startActivity(Intent(this, DogFetcherActivity::class.java))
    }

}
