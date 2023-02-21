package com.course.capstone.littlelemon.network

import android.util.Log
import com.course.capstone.littlelemon.components.Constants
import com.course.capstone.littlelemon.network.dto.Meal
import com.course.capstone.littlelemon.network.dto.Menu
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.serialization.decodeFromString


class MealServiceImpl(
    private val client: HttpClient
) : MealService {

    override suspend fun getMeals():  List<Meal> {

        var data = Menu(emptyList())
        try {
           data = client.get<Menu> {
                url(Constants.BASE_URL)
            }

        }  catch (e: NoTransformationFoundException) {
            val mealsString: String = client.get(Constants.BASE_URL)
            Log.d("clientGet", "MealServiceImpl | mealsString: ${mealsString}")

            val json = kotlinx.serialization.json.Json {
                ignoreUnknownKeys = true
            }
            data = json.decodeFromString(mealsString)
        } finally {
            Log.d("clientGet", "MealServiceImpl | getMeals: ${data.meals.size}, ${data.meals}")
        }
        return data.meals
    }

}