package com.example.easyfood.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.easyfood.data.Meal
import com.example.easyfood.databinding.MealItemBinding

class FavouriteMealsAdapter :
    RecyclerView.Adapter<FavouriteMealsAdapter.FavouriteMealsAdapterViewHolder>() {

    inner class FavouriteMealsAdapterViewHolder(val binding: MealItemBinding):RecyclerView.ViewHolder(binding.root)

    lateinit var onItemClick:((meal:Meal)->Unit)

    private val diffUtil= object : DiffUtil.ItemCallback<Meal>(){
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.idMeal==newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem==newItem
        }
    }
    val differ=AsyncListDiffer(this,diffUtil)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavouriteMealsAdapterViewHolder {
        return FavouriteMealsAdapterViewHolder(
            MealItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: FavouriteMealsAdapterViewHolder, position: Int) {
        val meal=differ.currentList[position]
        Glide.with(holder.itemView)
            .load(meal.strMealThumb)
            .into(holder.binding.imgMealCatagoryByName)

        holder.binding.tvMealname.text= meal.strMeal?.let { truncateToTwoWords(it) }
        holder.itemView.setOnClickListener {
            onItemClick.invoke(meal)
        }
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