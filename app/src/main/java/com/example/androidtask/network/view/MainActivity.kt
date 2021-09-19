package com.example.androidtask.network.view


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.androidtask.R


class MainActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragment()


    }



    fun replaceFragment(){
       supportFragmentManager
           .beginTransaction()
           .replace(R.id.container,FilesListFragment())
           .commit()
    }
}


