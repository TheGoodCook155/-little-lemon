package com.course.capstone.littlelemon.network

import com.course.capstone.littlelemon.network.dto.Meal
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*

interface MealService {

    suspend fun getMeals(): List<Meal>

    companion object {
        fun create(): MealService {
            return MealServiceImpl(
                client = HttpClient(Android) {
                    install(Logging) {
                        level = LogLevel.ALL
                    }
                    install(JsonFeature) {
                        serializer = KotlinxSerializer()
                    }
                }
            )
        }
    }
}