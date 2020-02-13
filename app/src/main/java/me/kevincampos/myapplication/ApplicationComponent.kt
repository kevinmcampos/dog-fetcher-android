package me.kevincampos.myapplication

import dagger.Component
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Component
@Singleton
interface ApplicationComponent : AndroidInjector<DogApplication> {

}