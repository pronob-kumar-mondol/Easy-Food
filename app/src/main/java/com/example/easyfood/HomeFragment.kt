package com.example.easyfood

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.easyfood.data.Meal
import com.example.easyfood.databinding.FragmentHomeBinding
import com.example.easyfood.viewmodel.HomeViewModel
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel:HomeViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            homeViewModel.getRandomMeal()
        }

//        homeViewModel.getRandomMeal()
        observeRandomMeal()

    }

    private fun observeRandomMeal() {
        homeViewModel.randomMealLiveData.observe(viewLifecycleOwner){meal->
            meal?.let {
                Log.d("HomeFragment", "Random Meal Fetched: $meal")
                Glide.with(this)
                    .load(it.strMealThumb)
                    .into(binding.imgRandomMeal)
            }
        }
    }


}