package com.example.easyfood.retrofit

import com.example.easyfood.data.CatagoryMealList
import com.example.easyfood.data.PopularMealsList
import com.example.easyfood.data.MealList
import com.example.easyfood.data.MealsByCatagoryList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {

    @GET("random.php")
    fun getRandomMeal():Call<MealList>

    @GET("lookup.php")
    fun getMealDetails(@Query("i") id:String):Call<MealList>

    @GET("filter.php")
    fun getPopularItems(@Query("c") catagoryName:String):Call<PopularMealsList>

    @GET("categories.php")
    fun getCatagoryMeal():Call<CatagoryMealList>

    @GET("filter.php")
    fun getCatagoryMealListByName(@Query("c") catagoryMealName:String):Call<MealsByCatagoryList>

    @GET("search.php")
    fun searchMeals(@Query("s") searchQuery:String):Call<MealList>
}