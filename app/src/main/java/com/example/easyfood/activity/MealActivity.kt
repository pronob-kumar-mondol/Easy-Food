package com.example.easyfood.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.easyfood.R
import com.example.easyfood.data.Meal
import com.example.easyfood.databinding.ActivityMealBinding
import com.example.easyfood.fragment.HomeFragment
import com.example.easyfood.roomDB.MealDatabase
import com.example.easyfood.viewmodel.MealViewModel
import com.example.easyfood.viewmodel.factory.MealViewModelFactory

class MealActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMealBinding
    private lateinit var viewModel:MealViewModel
    private lateinit var meadId:String
    private lateinit var mealName:String
    private lateinit var mealThumb:String
    private lateinit var youtubeLink:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Initialize ViewModel with ViewModelFactory
        val mealDatabase=MealDatabase.getInstance(this)
        val viewModelFactory = MealViewModelFactory(mealDatabase)
        viewModel = ViewModelProvider(this, viewModelFactory)[MealViewModel::class.java]

        //Getting Random meal info from Home Fragment by Intent
        getRandomMealInfoFromIntent()

        //Set the intent infos in views
        setInfoToViews()

        //Getting meal details
        viewModel.getMealDetails(meadId)
        //Observe meal details live data
        obserevMealDetail()

        //Youtube click Handle
        onClickYoutube()

        //Save Favorite Meal In Database
        onFavouriteClick()

    }

    private fun onFavouriteClick() {
        binding.fabMealDetails.setOnClickListener {
            mealToSave?.let {
                viewModel.insertMeal(it)
                Toast.makeText(this,"Meal Saved", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun onClickYoutube() {
        binding.youtube.setOnClickListener{
            val intent= Intent(Intent.ACTION_VIEW,Uri.parse(youtubeLink))
            startActivity(intent)
        }
    }

    private var mealToSave: Meal? =null
    private fun obserevMealDetail() {
        viewModel.mealDeatailsLivedata.observe(this
        ) {meal->
            mealToSave=meal
            binding.tvCatagoryName.text = "Catagory : ${meal?.strCategory}"
            binding.tvArea.text="Area : ${meal?.strArea}"
            binding.instructionSteps.text=meal?.strInstructions

            youtubeLink= meal?.strYoutube.toString()


        }
    }

    private fun setDetailsToViews() {

    }

    private fun setInfoToViews() {
        //Change Appbar ImageView
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.imgMealDetail)

        //Changing Appbar Text View and color
        binding.colapsingToolbar.title=mealName
        binding.colapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.colapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.white))
        binding.colapsingToolbar.collapsedTitleTextSize=38f
        binding.colapsingToolbar.expandedTitleTextSize=65f

    }

    private fun getRandomMealInfoFromIntent() {
        val intent=intent

        meadId=intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName=intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb=intent.getStringExtra(HomeFragment.MEAL_THUMB)!!
    }
}