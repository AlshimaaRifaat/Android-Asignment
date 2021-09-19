package com.example.androidtask.network.repository


import com.example.androidtask.network.model.FilesListResponse
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET



interface APIService {

    @GET("/v1/52931552-80ec-46a3-ba82-bc0bd0d2eac7")
    fun getFilesList(): Single<FilesListResponse>
}