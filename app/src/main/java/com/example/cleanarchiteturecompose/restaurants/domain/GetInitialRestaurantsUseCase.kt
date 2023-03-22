package com.example.cleanarchiteturecompose.restaurants.domain

import com.example.cleanarchiteturecompose.restaurants.data.RestaurantsRepository
import javax.inject.Inject

class GetInitialRestaurantsUseCase @Inject constructor(
    private val repository: RestaurantsRepository ,
    private val getSortedRestaurantsUseCase:GetSortedRestaurantsUseCase,
){suspend operator fun invoke():List<Restaurant>{
        repository.loadsRestaurants()
        return getSortedRestaurantsUseCase()
    }
}