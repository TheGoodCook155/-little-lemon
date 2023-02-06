package com.course.capstone.littlelemon.network

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
class MenuNetworkData(

    @SerialName("id")
    val id: String,

    @SerialName("title")
    val title: String,

    @SerialName("description")
    val description: String,

    @SerialName("price")
    val price: Int,

    @SerialName("image")
    val image: String,

    @SerialName("category")
    val category: String
)
