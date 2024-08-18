package com.example.easyfood.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.easyfood.data.CatagoryList
import com.example.easyfood.data.CatagoryMeals
import com.example.easyfood.data.Meal
import com.example.easyfood.data.MealList
import com.example.easyfood.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel:ViewModel() {

    private val _randomMealLiveData = MutableLiveData<Meal?>()
    val randomMealLiveData: LiveData<Meal?> get() = _randomMealLiveData

    private val _popularItemLiveData=MutableLiveData<List<CatagoryMeals>?>()
    val popularItemLiveData:LiveData<List<CatagoryMeals>?> get()=_popularItemLiveData


    fun getRandomMeal(){
        RetrofitInstance.api.getRandomMeal().enqueue(object :Callback<MealList>{
            override fun onResponse(p0: Call<MealList>, response: Response<MealList>) {
                if (response.isSuccessful){
                    val randomMeal = response.body()?.meals?.get(0)
                    _randomMealLiveData.postValue(randomMeal)
                    Log.d("HomeViewModel", "Random Meal Fetched: $randomMeal")
                }else{
                    Log.e("HomeViewModel", "Error fetching random meal: ${response.code()}")
                    return
                }
            }

            override fun onFailure(p0: Call<MealList>, response: Throwable) {
                Log.e("HomeViewModel", "Network Error: ${response.message}",response)
            }
        })
    }

    fun getPopularItems(){
        RetrofitInstance.api.getPopularItems("Seafood").enqueue(object :Callback<CatagoryList>{
            override fun onResponse(p0: Call<CatagoryList>, p1: Response<CatagoryList>) {
                if (p1.isSuccessful){
                    val popularItem= p1.body()?.meals
                    _popularItemLiveData.postValue(popularItem)
                }else
                    return
            }

            override fun onFailure(p0: Call<CatagoryList>, p1: Throwable) {
                Log.d("HomeFragment",p1.message.toString())
            }

        })
    }

}