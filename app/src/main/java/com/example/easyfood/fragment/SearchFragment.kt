package com.example.easyfood.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.easyfood.R
import com.example.easyfood.activity.MainActivity
import com.example.easyfood.adapters.FavouriteMealsAdapter
import com.example.easyfood.databinding.FragmentSearchBinding
import com.example.easyfood.viewmodel.HomeViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {

    private lateinit var binding:FragmentSearchBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var searchRVadapter:FavouriteMealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel=(activity as MainActivity).homeViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentSearchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()

        binding.arrowBack.setOnClickListener { searchMeals() }
        observeSearchMealsLiveData()

        var searchJob:Job?=null
        binding.searchEt.addTextChangedListener{
            searchJob?.cancel()
            searchJob=lifecycleScope.launch {
                delay(500)
                viewModel.searchMeals(it.toString())
            }
        }



    }

    private fun observeSearchMealsLiveData() {
        viewModel.searchedMealLiveData.observe(viewLifecycleOwner){mealsList->
            searchRVadapter.differ.submitList(mealsList)
        }
    }

    private fun searchMeals() {
        val searchQuery=binding.searchEt.text.toString()

        if (searchQuery.isNotEmpty()){
            viewModel.searchMeals(searchQuery)
        }
    }

    private fun prepareRecyclerView() {
        searchRVadapter=FavouriteMealsAdapter()
        binding.rvSearch.apply {
            layoutManager=GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false  )
            adapter=searchRVadapter
        }
    }


}