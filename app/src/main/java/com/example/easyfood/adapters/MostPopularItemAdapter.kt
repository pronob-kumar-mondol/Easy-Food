package com.example.easyfood.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.easyfood.data.PopularMeals
import com.example.easyfood.databinding.PopularItemsBinding

class MostPopularItemAdapter(private var mealList:List<PopularMeals>) :RecyclerView.Adapter<MostPopularItemAdapter.PopularItemViewHolder>() {

    lateinit var onItemClick:((PopularMeals)->Unit)

    inner class PopularItemViewHolder(val binding: PopularItemsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularItemViewHolder {
        return PopularItemViewHolder(PopularItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return mealList.size
    }

    override fun onBindViewHolder(holder: PopularItemViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(mealList[position].strMealThumb)
            .into(holder.binding.imgPopularMealItem)

        holder.itemView.setOnClickListener {
            onItemClick.invoke(mealList[position])
        }
    }

    fun setMeals(mealslist:List<PopularMeals>){
        this.mealList=mealslist
        notifyDataSetChanged()
    }


}