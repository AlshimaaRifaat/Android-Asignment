package com.example.androidtask.network.model

data class FilesListResponseItem(
    val id: String,
    val name: String,
    val type: String,
    val url: String,
    var isChecked:Boolean=false
)