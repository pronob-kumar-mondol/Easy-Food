package com.example.easyfood.retrofit

import com.example.easyfood.data.CatagoryList
import com.example.easyfood.data.Meal
import com.example.easyfood.data.MealList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {

    @GET("random.php")
    fun getRandomMeal():Call<MealList>

    @GET("lookup.php?")
    fun getMealDetails(@Query("i") id:String):Call<MealList>

    @GET("filter.php?")
    fun getPopularItems(@Query("c") catagoryName:String):Call<CatagoryList>
}