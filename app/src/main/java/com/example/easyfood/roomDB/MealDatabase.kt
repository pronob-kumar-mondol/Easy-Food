package com.example.easyfood.roomDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.easyfood.data.Meal

@Database(entities = [Meal::class], version = 1 )
@TypeConverters(MealTypeCoverter::class)
abstract class MealDatabase:RoomDatabase() {
    abstract fun mealDao():MealDAO

    companion object{

        @Volatile
        private var INSTANCE:MealDatabase?=null

        @Synchronized
        fun getInstance(context: Context):MealDatabase{
            if(INSTANCE==null){
                synchronized(this){
                    INSTANCE= Room.databaseBuilder(
                        context,
                        MealDatabase::class.java,
                        "meal.db"
                    ).fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE!!
        }
    }

}