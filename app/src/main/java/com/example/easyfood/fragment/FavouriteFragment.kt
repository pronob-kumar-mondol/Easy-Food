package com.example.easyfood.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.easyfood.R
import com.example.easyfood.activity.MainActivity
import com.example.easyfood.activity.MealActivity
import com.example.easyfood.adapters.FavouriteMealsAdapter
import com.example.easyfood.databinding.FragmentFavouriteBinding
import com.example.easyfood.fragment.HomeFragment.Companion.MEAL_ID
import com.example.easyfood.fragment.HomeFragment.Companion.MEAL_NAME
import com.example.easyfood.fragment.HomeFragment.Companion.MEAL_THUMB
import com.example.easyfood.viewmodel.HomeViewModel


class FavouriteFragment : Fragment() {

    private lateinit var binding:FragmentFavouriteBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var favouriteAdapter:FavouriteMealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel=(activity as MainActivity).homeViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentFavouriteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()
        observeFavouriteLiveData()

        onFavouriteMealClick()
    }

    private fun onFavouriteMealClick() {
        favouriteAdapter.onItemClick={meal->
            val intent= Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID,meal.idMeal)
            intent.putExtra(MEAL_NAME,meal.strMeal)
            intent.putExtra(MEAL_THUMB,meal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun setUpRecyclerView() {
        favouriteAdapter=FavouriteMealsAdapter()
        binding.rvMealsByFavourite.apply {
            layoutManager=GridLayoutManager(context,2, GridLayoutManager.VERTICAL,false)
            adapter=favouriteAdapter
        }
    }

    private fun observeFavouriteLiveData() {
        viewModel.favouriteMealsLiveData.observe(viewLifecycleOwner){meal->
            favouriteAdapter.differ.submitList(meal)
        }
    }

}