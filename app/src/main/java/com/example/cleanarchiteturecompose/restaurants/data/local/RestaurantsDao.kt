package com.example.cleanarchiteturecompose.restaurants.data.local

import androidx.room.*

@Dao
interface RestaurantsDao {
    @Query("SELECT * FROM restaurants")
    fun getAll(): List<LocalRestaurant>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAll(restaurants: List<LocalRestaurant>)

    @Update(entity = LocalRestaurant::class)
    fun update(partialRestaurant: PartialRestaurant)

    @Update(entity = LocalRestaurant::class)
    fun updateAll(partialRestaurants: List<PartialRestaurant>)

    @Query("SELECT * FROM restaurants WHERE is_favorite = 1")
    fun getAllFavorited(): List<LocalRestaurant>

}

