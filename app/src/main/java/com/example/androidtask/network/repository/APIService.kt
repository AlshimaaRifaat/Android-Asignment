package com.example.androidtask.network.repository


import com.example.androidtask.network.model.FilesListResponse
import retrofit2.Call
import retrofit2.http.GET



interface APIService {

    @GET("/v1/870dee60-47a0-44d8-a7d1-75ff7954f531")
    fun makeRequest(): Call<FilesListResponse>
}