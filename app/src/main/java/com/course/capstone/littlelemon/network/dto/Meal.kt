package com.course.capstone.littlelemon.network.dto


@kotlinx.serialization.Serializable
data class Meal(

//    @SerialName("id")
    val id: String,

//    @SerialName("title")
    val title: String,

//    @SerialName("description")
    val description: String,

//    @SerialName("price")
    val price: Int,

//    @SerialName("image")
    val image: String,

//    @SerialName("category")
    val category: String
)
