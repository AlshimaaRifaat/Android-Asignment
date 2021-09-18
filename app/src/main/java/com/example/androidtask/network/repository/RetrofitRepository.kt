package com.example.androidtask.network.repository


import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.androidtask.MyRetroApplication
import com.example.androidtask.network.di.APIComponent
import com.example.androidtask.network.model.FilesListResponse

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

class RetrofitRepository {
    lateinit var apiComponent: APIComponent
    var postInfoMutableList: MutableLiveData<FilesListResponse> = MutableLiveData()
    @Inject
    lateinit var retrofit: Retrofit
    init {
       /* apiComponent =   DaggerAPIComponent
            .builder()
            .aPIModule(APIModule(APIURL.BASE_URL))
            .build()
        apiComponent.inject(this)*/

        var apiComponent :APIComponent =  MyRetroApplication.apiComponent
        apiComponent.inject(this)
    }


    fun fetchPostInfoList(): LiveData<FilesListResponse> {

         var apiService:APIService = retrofit.create(APIService::class.java)
         var postListInfo : Call<FilesListResponse> =  apiService.makeRequest()
        postListInfo.enqueue(object :Callback<FilesListResponse>{
            override fun onResponse(
                call: Call<FilesListResponse>,
                response: Response<FilesListResponse>
            ) {
                var postInfoList = response.body()
                postInfoMutableList.value = postInfoList


            }

            override fun onFailure(call: Call<FilesListResponse>, t: Throwable) {
                TODO("Not yet implemented")
                Log.d("RetroRepository","Failed:::"+t.message)
            }

        })

         return  postInfoMutableList

    }


}