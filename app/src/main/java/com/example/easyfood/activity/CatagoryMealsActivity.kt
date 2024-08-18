package com.example.easyfood.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.easyfood.R
import com.example.easyfood.adapters.MealsByCatagoryNameAdapter
import com.example.easyfood.databinding.ActivityCatagoryMealsBinding
import com.example.easyfood.fragment.HomeFragment
import com.example.easyfood.viewmodel.MealsByCatagoryViewModel

class CatagoryMealsActivity : AppCompatActivity() {

    private lateinit var binding:ActivityCatagoryMealsBinding
    private val viewModel:MealsByCatagoryViewModel by viewModels()
    private lateinit var _adapter: MealsByCatagoryNameAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityCatagoryMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getMealsByCatagoryName(intent.getStringExtra(HomeFragment.CATAGORY_MEAL_NAME)!!)

        viewModel.mealsByCatagoryNameLiveData.observe(this){mealList->
            mealList.forEach {
                _adapter.setMealDetails(mealList)
            }
        }

        prepareRecyclerView()

    }

    private fun prepareRecyclerView() {
        _adapter=MealsByCatagoryNameAdapter(listOf())

        binding.rvMealsByCatagory.apply {
            layoutManager=GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter=_adapter
        }
    }


}