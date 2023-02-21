package com.course.capstone.littlelemon.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.course.capstone.littlelemon.network.dto.Meal
import com.course.capstone.littlelemon.repository.NetworkRepository
import kotlinx.coroutines.launch

class AppViewModel() : ViewModel() {

    private val repository: NetworkRepository = NetworkRepository.getInstance()

    private var _meals: List<Meal> = emptyList()


    init {
        viewModelScope.launch {
            _meals = repository.getMeals()
        }
        Log.d("clientGet", "AppViewModel | _meals: ${_meals.size}, ${_meals}")
    }

    fun getMeals(): List<Meal>{
        return _meals
    }


}