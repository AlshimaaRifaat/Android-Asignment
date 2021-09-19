package com.example.androidtask

import android.app.Application
import android.content.Context
import com.example.androidtask.network.di.APIComponent
import com.example.androidtask.network.di.APIModule
import com.example.androidtask.network.di.DaggerAPIComponent
import com.example.androidtask.network.repository.APIURL


class MoviesApplication : Application() {


    companion object {
        var ctx: Context? = null
        lateinit var apiComponent: APIComponent
    }
    override fun onCreate() {
        super.onCreate()
        ctx = applicationContext
        apiComponent = initDaggerComponent()

    }

    fun getMyComponent(): APIComponent {
        return apiComponent
    }

    fun initDaggerComponent(): APIComponent {
        apiComponent =   DaggerAPIComponent
            .builder()
            .aPIModule(APIModule(APIURL.BASE_URL))
            .build()
        return  apiComponent

    }
}