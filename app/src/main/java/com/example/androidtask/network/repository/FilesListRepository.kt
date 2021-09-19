package com.example.androidtask.network.repository


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.androidtask.MoviesApplication
import com.example.androidtask.network.di.APIComponent
import com.example.androidtask.network.model.FilesListResponse
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

import retrofit2.Retrofit
import javax.inject.Inject

class FilesListRepository {
    lateinit var apiComponent: APIComponent
    var FileInfoMutableList: MutableLiveData<FilesListResponse> = MutableLiveData()
    @Inject
    lateinit var retrofit: Retrofit
    init {
       /* apiComponent =   DaggerAPIComponent
            .builder()
            .aPIModule(APIModule(APIURL.BASE_URL))
            .build()
        apiComponent.inject(this)*/

        var apiComponent :APIComponent =  MoviesApplication.apiComponent
        apiComponent.inject(this)
    }


    fun fetchPostInfoList(): LiveData<FilesListResponse> {

         var apiService:APIService = retrofit.create(APIService::class.java)
         var observable : Single<FilesListResponse> =  apiService.getFilesList()
             .subscribeOn(Schedulers.io())
             .observeOn(AndroidSchedulers.mainThread());

        observable.subscribe { o->FileInfoMutableList.setValue(o) }

        return  FileInfoMutableList

    }


}