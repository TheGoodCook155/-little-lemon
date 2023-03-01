package com.course.capstone.littlelemon.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.course.capstone.littlelemon.datastore.AppSerializer
import com.course.capstone.littlelemon.datastore.StoreValues
import com.course.capstone.littlelemon.db.AppDatabase
import com.course.capstone.littlelemon.db.DbDao
import com.course.capstone.littlelemon.db.MealsDataEntity
import com.course.capstone.littlelemon.network.dto.Meal
import com.course.capstone.littlelemon.repository.DbRepository
import com.course.capstone.littlelemon.repository.NetworkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AppViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: NetworkRepository = NetworkRepository.getInstance()
    private var _meals: List<Meal> = emptyList()

    private val db = AppDatabase.getInstance(application)
    private val dbRepository = DbRepository(db.mealsDao())


    fun getMeals(): List<Meal>{
        return _meals
    }

    fun addMealIntoDb(meal: MealsDataEntity){

        viewModelScope.launch(Dispatchers.IO) {

            dbRepository.insertMeal(meal)
        }

    }

    private var _mealsFromDb = MutableStateFlow<List<MealsDataEntity>>(emptyList())
    val mealsFromDb = _mealsFromDb.asStateFlow()
    suspend fun getAllDbMeals(){

        dbRepository.getAllMeals().collect{ meals ->
            _mealsFromDb.value = meals
        }

    }

    init {
        Log.d("appCycle", ": AppViewModel init block starts")
        viewModelScope.launch {
            _meals = repository.getMeals()
            Log.d("clientGet", "AppViewModel | _meals: ${_meals.size}, ${_meals}")

            _meals.forEach { meal ->

                Log.d("clientGet", "AppViewModel | _meals forEach: ${_meals.size}, meal: ${meal}")


                var mealDataEntity = MealsDataEntity(
                    meal.id,
                    meal.title,
                    meal.description,
                    meal.price,
                    meal.image,
                    meal.category
                )

                Log.d("clientGet", "AppViewModel | mealDataEntity forEach: ${mealDataEntity}")

                addMealIntoDb(mealDataEntity)

            }

            Log.d("appCycle", ": AppViewModel init block ends")

            getAllDbMeals()

        }


    }




}