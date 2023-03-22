package com.example.cleanarchiteturecompose.restaurants.domain

import java.io.FileDescriptor

data class Restaurant (
    val id:Int,
    val title:String,
    val description: String,
    val isFavorite:Boolean = false
        )