package com.course.capstone.littlelemon.network.dto


@kotlinx.serialization.Serializable
data class Meal(

    val id: String,

    val title: String,

    val description: String,

    val price: Int,

    val image: String,

    val category: String
)
