package com.course.capstone.littlelemon.network

import com.course.capstone.littlelemon.components.Constants
import com.course.capstone.littlelemon.network.dto.Meal
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*


class MealServiceImpl(
    private val client: HttpClient
) : MealService {

    override suspend fun getMeals(): List<Meal> {
        return try {
            client.get {
                url(Constants.BASE_URL)
            }
        } catch(e: RedirectResponseException) {
            // 3xx - responses
            println("Error: ${e.response.status.description}")
            emptyList<Meal>()

        } catch(e: ClientRequestException) {
            // 4xx - responses
            println("Error: ${e.response.status.description}")
            emptyList<Meal>()


        } catch(e: ServerResponseException) {
            // 5xx - responses
            println("Error: ${e.response.status.description}")
            emptyList<Meal>()


        } catch(e: Exception) {
            println("Error: ${e.message}")
            emptyList<Meal>()

        }
    }

}