package com.example.easyfood.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.easyfood.data.CatagoryMealList
import com.example.easyfood.data.Category
import com.example.easyfood.data.PopularMealsList
import com.example.easyfood.data.PopularMeals
import com.example.easyfood.data.Meal
import com.example.easyfood.data.MealList
import com.example.easyfood.retrofit.RetrofitInstance
import com.example.easyfood.roomDB.MealDatabase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(
    private val mealDatabase: MealDatabase
):ViewModel() {

    private val _randomMealLiveData = MutableLiveData<Meal?>()
    val randomMealLiveData: LiveData<Meal?> get() = _randomMealLiveData

    private val _popularItemLiveData=MutableLiveData<List<PopularMeals>?>()
    val popularItemLiveData:LiveData<List<PopularMeals>?> get()=_popularItemLiveData

    private val _catagoriesMealLiveData=MutableLiveData<List<Category>>()
    val catagoriesMealLiveData: LiveData<List<Category>?> get() = _catagoriesMealLiveData

    val favouriteMealsLiveData=mealDatabase.mealDao().getAllMeals()

    private val _searchedMealLiveData=MutableLiveData<List<Meal>?>()
    val searchedMealLiveData:LiveData<List<Meal>?> get() = _searchedMealLiveData


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
        RetrofitInstance.api.getPopularItems("Seafood").enqueue(object :Callback<PopularMealsList>{
            override fun onResponse(p0: Call<PopularMealsList>, p1: Response<PopularMealsList>) {
                if (p1.isSuccessful){
                    val popularItem= p1.body()?.meals
                    _popularItemLiveData.postValue(popularItem)
                }else
                    return
            }

            override fun onFailure(p0: Call<PopularMealsList>, p1: Throwable) {
                Log.d("HomeFragment",p1.message.toString())
            }

        })
    }

    fun getCatagoryMeal(){
        RetrofitInstance.api.getCatagoryMeal().enqueue(object :Callback<CatagoryMealList>{
            override fun onResponse(p0: Call<CatagoryMealList>, p1: Response<CatagoryMealList>) {
                if (p1.isSuccessful){
                    val catagoryItem=p1.body()!!.categories
                    _catagoriesMealLiveData.postValue(catagoryItem)
                }
            }

            override fun onFailure(p0: Call<CatagoryMealList>, p1: Throwable) {
                Log.d("HomeFragment",p1.message.toString())
            }

        })
    }

    fun searchMeals(searchQuery:String){
        RetrofitInstance.api.searchMeals(searchQuery).enqueue(object :Callback<MealList>{
            override fun onResponse(p0: Call<MealList>, p1: Response<MealList>) {
                val mealList=p1.body()?.meals
                if (p1.isSuccessful){
                    _searchedMealLiveData.postValue(mealList)
                }
            }

            override fun onFailure(p0: Call<MealList>, p1: Throwable) {
                Log.d("HomeFragment",p1.message.toString())
            }
        })
    }

}