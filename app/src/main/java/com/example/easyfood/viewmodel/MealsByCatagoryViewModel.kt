package com.example.easyfood.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.easyfood.data.MealsByCatagory
import com.example.easyfood.data.MealsByCatagoryList
import com.example.easyfood.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealsByCatagoryViewModel:ViewModel() {

    private var _mealsByCatagoryNameLiveData=MutableLiveData<List<MealsByCatagory?>>()
    val mealsByCatagoryNameLiveData:LiveData<List<MealsByCatagory?>> get() = _mealsByCatagoryNameLiveData

    fun getMealsByCatagoryName(mealName:String){
        RetrofitInstance.api.getCatagoryMealListByName(mealName).enqueue(object :Callback<MealsByCatagoryList>{
            override fun onResponse(
                p0: Call<MealsByCatagoryList>,
                p1: Response<MealsByCatagoryList>
            ) {
                if (p1.isSuccessful){
                    var data=p1.body()!!.meals
                    _mealsByCatagoryNameLiveData.postValue(data)
                }
            }

            override fun onFailure(p0: Call<MealsByCatagoryList>, p1: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }


}