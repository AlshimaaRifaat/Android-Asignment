package com.example.androidtask.network.di


import com.example.androidtask.AppModule
import com.example.androidtask.network.repository.FilesListRepository
import com.example.androidtask.network.view.FilesListFragment
import com.example.androidtask.network.viewmodel.FilesListViewModel
import com.example.androidtask.network.viewmodel.FilesListViewModelFactory
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class,APIModule::class])
interface APIComponent {
    fun inject(filesListRepository: FilesListRepository)
    fun inject(filesListViewModel: FilesListViewModel)
    fun inject(filesListFragment: FilesListFragment)
    fun inject(filesListViewModelFactory: FilesListViewModelFactory)
}