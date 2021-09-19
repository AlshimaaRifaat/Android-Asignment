package com.example.androidtask

import dagger.Module
import dagger.Provides

@Module
class AppModule constructor(myRetroApplication: MoviesApplication){

    var moviesApplication:MoviesApplication

    init {
        this.moviesApplication = myRetroApplication
    }

    @Provides
    fun provideMyRetroApplication():MoviesApplication{
        return moviesApplication
    }
}