package me.kevincampos.myapplication

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class DogApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerApplicationComponent.builder().build()
    }


}