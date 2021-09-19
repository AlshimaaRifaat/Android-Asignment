package com.example.androidtask.network.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.androidtask.MoviesApplication
import com.example.androidtask.network.di.APIComponent
import com.example.androidtask.network.di.APIModule
import com.example.androidtask.network.di.DaggerAPIComponent
import com.example.androidtask.network.repository.APIURL
import com.example.androidtask.network.repository.FilesListRepository


import javax.inject.Inject


class FilesListViewModelFactory : ViewModelProvider.Factory {
    lateinit var apiComponent: APIComponent
    @Inject
    lateinit var retrofitRepository: FilesListRepository

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
     //   initDaggerComponent()
       var apiComponent :APIComponent =  MoviesApplication.apiComponent
        apiComponent.inject(this)
        if (modelClass.isAssignableFrom(FilesListViewModel::class.java)) {
            return FilesListViewModel(retrofitRepository) as T
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