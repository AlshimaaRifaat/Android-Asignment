package com.example.androidtask.network.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.androidtask.MyRetroApplication
import com.example.androidtask.network.di.APIComponent
import com.example.androidtask.network.di.APIModule
import com.example.androidtask.network.di.DaggerAPIComponent
import com.example.androidtask.network.repository.APIURL
import com.example.androidtask.network.repository.RetrofitRepository


import javax.inject.Inject


class RetroViewModelFactory : ViewModelProvider.Factory {
    lateinit var apiComponent: APIComponent
    @Inject
    lateinit var retrofitRepository: RetrofitRepository

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
     //   initDaggerComponent()
       var apiComponent :APIComponent =  MyRetroApplication.apiComponent
        apiComponent.inject(this)
        if (modelClass.isAssignableFrom(RetroViewModel::class.java)) {
            return RetroViewModel(retrofitRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

    fun initDaggerComponent(){
        apiComponent =  DaggerAPIComponent
            .builder()
            .aPIModule(APIModule(APIURL.BASE_URL))
            .build()
        apiComponent.inject(this)
    }
}