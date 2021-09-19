package com.example.androidtask.network.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidtask.network.model.FilesListResponse
import com.example.androidtask.network.repository.FilesListRepository

class FilesListViewModel(retrofitRepository: FilesListRepository): ViewModel() {

    lateinit var retrofitRepository:FilesListRepository
    var postInfoLiveData: LiveData<FilesListResponse> = MutableLiveData()

    init {
        this.retrofitRepository  = retrofitRepository
        fetchFilesInfoFromRepository()
        }

    fun fetchFilesInfoFromRepository(){
        postInfoLiveData =  retrofitRepository.fetchPostInfoList()
    }


}