package com.example.easyfood.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.easyfood.roomDB.MealDatabase
import com.example.easyfood.viewmodel.MealViewModel

@Suppress("UNCHECKED_CAST")
class MealViewModelFactory(
    private val mealDatabase: MealDatabase
):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MealViewModel(mealDatabase) as T
    }
}