package com.example.cleanarchiteturecompose.restaurants.presentation.list

import com.example.cleanarchiteturecompose.restaurants.domain.Restaurant

data class RestaurantsScreenState (
    val restaurants:List<Restaurant>,
    val isLoading :Boolean,
    val error:String?= null
        )