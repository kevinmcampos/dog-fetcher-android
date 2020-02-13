package me.kevincampos.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import me.kevincampos.myapplication.challenge1.LibraryOneActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun openChallengeOne(view: View) {
        startActivity(Intent(this, LibraryOneActivity::class.java))
    }

}
