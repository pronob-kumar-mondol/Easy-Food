package com.example.easyfood.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.easyfood.activity.MealActivity
import com.example.easyfood.adapters.MostPopularItemAdapter
import com.example.easyfood.data.Meal
import com.example.easyfood.databinding.FragmentHomeBinding
import com.example.easyfood.viewmodel.HomeViewModel


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var randomMeal: Meal
    private lateinit var popularItemAdapter: MostPopularItemAdapter
    private val homeViewModel:HomeViewModel by viewModels()

    companion object{
        const val MEAL_ID="com.example.easyfood.fragment.idMeal"
        const val MEAL_NAME="com.example.easyfood.fragment.nameMeal"
        const val MEAL_THUMB="com.example.easyfood.fragment.thumbMeal"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        popularItemAdapter=MostPopularItemAdapter(listOf())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (homeViewModel.randomMealLiveData.value == null) {
            homeViewModel.getRandomMeal()
        }
        observeRandomMeal()

        onRandomMealClick()

        homeViewModel.getPopularItems()

        observePopularItems()

        setPopularItemAdapter()

        onPopularItemClick()

    }

    private fun onPopularItemClick() {
        popularItemAdapter.onItemClick={meal->
            val intent=Intent(activity,MealActivity::class.java)
            intent.putExtra(MEAL_ID,meal.idMeal)
            intent.putExtra(MEAL_NAME,meal.strMeal)
            intent.putExtra(MEAL_THUMB,meal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun setPopularItemAdapter() {
        binding.rvPopularItem.apply {
            layoutManager=LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
            adapter=popularItemAdapter
        }
    }

    private fun observePopularItems() {
        homeViewModel.popularItemLiveData.observe(viewLifecycleOwner){meal->
            if (meal != null) {
                popularItemAdapter.setMeals(mealslist = meal)
            }
        }
    }

    private fun onRandomMealClick() {
        binding.cardRandomMeal.setOnClickListener {
            val intent= Intent(activity,MealActivity::class.java)
            intent.putExtra(MEAL_ID,randomMeal.idMeal)
            intent.putExtra(MEAL_NAME,randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB,randomMeal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun observeRandomMeal() {
        homeViewModel.randomMealLiveData.observe(viewLifecycleOwner){meal->
            meal?.let {
                Log.d("HomeFragment", "Random Meal Fetched: $meal")
                Glide.with(this)
                    .load(it.strMealThumb)
                    .into(binding.imgRandomMeal)

                this.randomMeal=meal
            }
        }
    }


}