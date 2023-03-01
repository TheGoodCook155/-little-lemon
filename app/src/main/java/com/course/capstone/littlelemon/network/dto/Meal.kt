package com.course.capstone.littlelemon.network.dto

import androidx.room.Entity


@kotlinx.serialization.Serializable
data class Meal(

    val id: Int,

    val title: String,

    val description: String,

    val price: String,

    val image: String,

    val category: String
)

/*
      "id": 1,
      "title": "Greek Salad",
      "description": "The famous greek salad of crispy lettuce, peppers, olives, our Chicago.",
      "price": "10",
      "image": "https://github.com/Meta-Mobile-Developer-PC/Working-With-Data-API/blob/main/images/greekSalad.jpg?raw=true",
      "category": "starters"
 */
