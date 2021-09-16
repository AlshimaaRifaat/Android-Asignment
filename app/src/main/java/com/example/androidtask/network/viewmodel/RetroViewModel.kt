package com.example.androidtask.network.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidtask.network.model.PostInfo
import com.example.androidtask.network.repository.RetrofitRepository

import javax.inject.Inject

class RetroViewModel(retrofitRepository: RetrofitRepository): ViewModel() {

    lateinit var retrofitRepository:RetrofitRepository
    var postInfoLiveData: LiveData<List<PostInfo>> = MutableLiveData()

    init {
        this.retrofitRepository  = retrofitRepository
        fetchPostInfoFromRepository()
        }

    fun fetchPostInfoFromRepository(){
        postInfoLiveData =  retrofitRepository.fetchPostInfoList()
    }


}