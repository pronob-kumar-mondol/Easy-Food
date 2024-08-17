package com.example.easyfood.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.easyfood.data.Meal
import com.example.easyfood.data.MealList
import com.example.easyfood.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MealViewModel:ViewModel() {

    private var _mealDeatailsLivedata=MutableLiveData<Meal?>()
    val mealDeatailsLivedata:LiveData<Meal?> get()= _mealDeatailsLivedata

    fun getMealDetails(id:String){
        RetrofitInstance.api.getMealDetails(id).enqueue(object :Callback<MealList>{
            override fun onResponse(p0: Call<MealList>, p1: Response<MealList>) {
                if (p1.isSuccessful){
                    val mealDetails=p1.body()?.meals?.get(0)
                    _mealDeatailsLivedata.postValue(mealDetails)
                }else
                    return
            }

            override fun onFailure(p0: Call<MealList>, p1: Throwable) {
                Log.d("Meal Activity", p1.message.toString())
            }

        })
    }
}