package com.example.easyfood.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.easyfood.R
import com.example.easyfood.activity.CatagoryMealsActivity
import com.example.easyfood.activity.MainActivity
import com.example.easyfood.activity.MealActivity
import com.example.easyfood.adapters.CatagoryMealAdapter
import com.example.easyfood.adapters.MostPopularItemAdapter
import com.example.easyfood.data.Meal
import com.example.easyfood.databinding.FragmentHomeBinding
import com.example.easyfood.viewmodel.HomeViewModel


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var randomMeal: Meal
    private lateinit var popularItemAdapter: MostPopularItemAdapter
    private lateinit var catagoryMealAdapter: CatagoryMealAdapter
    private lateinit var homeViewModel:HomeViewModel

    companion object{
        const val MEAL_ID="com.example.easyfood.fragment.idMeal"
        const val MEAL_NAME="com.example.easyfood.fragment.nameMeal"
        const val MEAL_THUMB="com.example.easyfood.fragment.thumbMeal"
        const val CATAGORY_MEAL_NAME="com.example.easyfood.fragment.catagoryMealName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        popularItemAdapter=MostPopularItemAdapter(listOf())
        catagoryMealAdapter=CatagoryMealAdapter(listOf())
        homeViewModel=(activity as MainActivity).homeViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=FragmentHomeBinding.inflate(layoutInflater)
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

        setCatagoryMealAdapter()
        homeViewModel.getCatagoryMeal()
        observeCatagoryItems()

        onCatagoryItemClick()

        onSearchbtnClick()


    }

    private fun onSearchbtnClick() {
        binding.imgSearch.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment2)
        }
    }

    private fun onCatagoryItemClick() {
        catagoryMealAdapter.onitemClick={catagory->
            val intent=Intent(activity,CatagoryMealsActivity::class.java)
            intent.putExtra(CATAGORY_MEAL_NAME,catagory.strCategory)
            startActivity(intent)
        }
    }

    private fun setCatagoryMealAdapter() {
        binding.rvCatagories.apply {
            layoutManager=GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
            adapter=catagoryMealAdapter
        }
    }

    private fun observeCatagoryItems() {
        homeViewModel.catagoriesMealLiveData.observe(viewLifecycleOwner){catagory->
            if (catagory!=null){
                catagoryMealAdapter.setCatagories(catagory)
            }
        }
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

                binding.tvMealName.text = meal.strMeal.toString()
            }
        }
    }


}