package com.example.cleanarchiteturecompose.restaurants.data

import com.example.cleanarchiteturecompose.restaurants.data.remote.RestaurantsApiService
import com.example.cleanarchiteturecompose.RestaurantsApplication
import com.example.cleanarchiteturecompose.restaurants.data.local.LocalRestaurant
import com.example.cleanarchiteturecompose.restaurants.data.local.PartialRestaurant
import com.example.cleanarchiteturecompose.restaurants.data.local.RestaurantsDao
import com.example.cleanarchiteturecompose.restaurants.data.local.RestaurantsDb
import com.example.cleanarchiteturecompose.restaurants.domain.Restaurant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.ConnectException
import java.net.UnknownHostException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RestaurantsRepository @Inject constructor(
    private val restInterface: RestaurantsApiService,
    private val restaurantsDao: RestaurantsDao
){

    suspend fun toggleFavoriteRestaurant(
        id: Int,
        oldValue:Boolean
    )=
        withContext(Dispatchers.IO){
            restaurantsDao.update(
                PartialRestaurant(id = id, isFavorite = !oldValue)
            )
            restaurantsDao.getAll()
        }

    suspend fun getRestaurants() : List<Restaurant> {
        return withContext(Dispatchers.IO) {
            return@withContext restaurantsDao.getAll().map {
                Restaurant(it.id, it.title, it.description, it.isFavorite)
            }
        }
    }

    suspend fun loadsRestaurants(){
        return withContext(Dispatchers.IO) {
            try {
                refreshCache()
            } catch (e: Exception) {
                when (e) {
                    is UnknownHostException,
                    is ConnectException,
                    is HttpException -> {
                        if (restaurantsDao.getAll().isEmpty())
                            throw Exception(
                                "Something went wrong. " +
                                        "We have no data."
                            )
                    }
                    else -> throw e
                }
            }


        }
    }
    private suspend fun refreshCache() {
        val remoteRestaurants = restInterface.getRestaurants()
        val favoriteRestaurants = restaurantsDao.getAllFavorited()
        restaurantsDao.addAll(remoteRestaurants.map {
            LocalRestaurant(it.id, it.title, it.description, false)
        })
        restaurantsDao.updateAll(
            favoriteRestaurants.map {
                PartialRestaurant(id = it.id, isFavorite = true)
            })
    }

}