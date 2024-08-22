package com.example.easyfood.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.easyfood.data.Meal
import com.example.easyfood.data.MealsByCatagory
import com.example.easyfood.databinding.MealItemBinding

class MealsByCatagoryNameAdapter(private var mealList:List<MealsByCatagory>):
    RecyclerView.Adapter<MealsByCatagoryNameAdapter.NameViewHolder>() {
    inner class NameViewHolder(val binding:MealItemBinding):RecyclerView.ViewHolder(binding.root)

    lateinit var onItemClick:((MealsByCatagory)->Unit)
    lateinit var onFabClick:((MealsByCatagory)->Unit)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NameViewHolder {
        return NameViewHolder(MealItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return mealList.size
    }

    override fun onBindViewHolder(holder: NameViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(mealList[position].strMealThumb)
            .into(holder.binding.imgMealCatagoryByName)

        holder.binding.tvMealname.text=truncateToTwoWords(mealList[position].strMeal)

        holder.itemView.setOnClickListener {
            onItemClick.invoke(mealList[position])
        }
        holder.binding.fabFavourite.setOnClickListener {
            onFabClick.invoke(mealList[position])
            Log.d("Fab Clicked","true")
        }
    }

    fun setMealDetails(mealList:List<MealsByCatagory?>){
        this.mealList= mealList as List<MealsByCatagory>
        notifyDataSetChanged()
    }

    private fun truncateToTwoWords(text: String): String {
        val words = text.split(" ")
        return if (words.size > 2) {
            words.take(2).joinToString(" ")
        } else {
            text
        }
    }


}