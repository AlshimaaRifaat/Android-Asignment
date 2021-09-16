package com.example.androidtask.network.di


import com.example.androidtask.AppModule
import com.example.androidtask.network.repository.RetrofitRepository
import com.example.androidtask.network.view.RetroFragment
import com.example.androidtask.network.viewmodel.RetroViewModel
import com.example.androidtask.network.viewmodel.RetroViewModelFactory
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class,APIModule::class])
interface APIComponent {
    fun inject(retrofitRepository: RetrofitRepository)
    fun inject(retroViewModel: RetroViewModel)
    fun inject(retroFragment: RetroFragment)
    fun inject(retroViewModelFactory: RetroViewModelFactory)
}