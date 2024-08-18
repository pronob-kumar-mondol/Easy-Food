package com.example.easyfood.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.easyfood.data.Category
import com.example.easyfood.databinding.CatagoryMealsItemBinding

class CatagoryMealAdapter(private var catagoryMeals:List<Category>):RecyclerView.Adapter<CatagoryMealAdapter.CatagoryMealViewHolder>() {

    lateinit var onitemClick:((Category)->Unit)
    inner class CatagoryMealViewHolder(val binding: CatagoryMealsItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CatagoryMealAdapter.CatagoryMealViewHolder {
        return CatagoryMealViewHolder(CatagoryMealsItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(
        holder: CatagoryMealAdapter.CatagoryMealViewHolder,
        position: Int
    ) {

        Glide.with(holder.itemView)
            .load(catagoryMeals[position].strCategoryThumb)
            .into(holder.binding.imgMealCatagory)

        holder.binding.tvMealCatagory.text=catagoryMeals[position].strCategory

        holder.itemView.setOnClickListener {
            onitemClick.invoke(catagoryMeals[position])
        }

    }

    override fun getItemCount(): Int {
        return catagoryMeals.size
    }

    fun setCatagories(catagoryMeals: List<Category>){
        this.catagoryMeals=catagoryMeals
        notifyDataSetChanged()
    }

}