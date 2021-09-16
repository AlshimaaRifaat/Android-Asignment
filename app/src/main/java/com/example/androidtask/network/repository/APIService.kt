package com.example.androidtask.network.repository


import com.example.androidtask.network.model.PostInfo
import retrofit2.Call
import retrofit2.http.GET



interface APIService {

    @GET("posts")
    fun makeRequest(): Call<List<PostInfo>>
}