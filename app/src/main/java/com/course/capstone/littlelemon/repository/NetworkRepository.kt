package com.course.capstone.littlelemon.repository

import android.util.Log
import com.course.capstone.littlelemon.network.MealService
import com.course.capstone.littlelemon.network.dto.Meal

class NetworkRepository private constructor(){

    private val service: MealService = MealService.create()

    companion object {
        @Volatile
        private lateinit var instance: NetworkRepository

        fun getInstance(): NetworkRepository {
            synchronized(this) {
                if (!::instance.isInitialized) {
                    instance = NetworkRepository()
                }
                return instance
            }
        }
    }

   suspend fun getMeals(): List<Meal> {
       val data = service.getMeals()
       Log.d("clientGet", "NetworkRepository | getMeals(): ${data.size}, ${data}")
       return  data
   }

}