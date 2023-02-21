package com.course.capstone.littlelemon.network.dto

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class Menu(
    @SerialName("menu")
    val meals: List<Meal>
)
