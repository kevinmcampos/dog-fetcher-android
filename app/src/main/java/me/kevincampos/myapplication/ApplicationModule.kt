package me.kevincampos.myapplication

import dagger.Module
import me.kevincampos.myapplication.challenge1.LibraryOneApi
import retrofit2.Retrofit

@Module
class ApplicationModule {

    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl("https://dog.ceo/")
            .build()
    }

    fun provideMarvelApi(retrofit: Retrofit): LibraryOneApi {
        return retrofit.create(LibraryOneApi::class.java)
    }

}