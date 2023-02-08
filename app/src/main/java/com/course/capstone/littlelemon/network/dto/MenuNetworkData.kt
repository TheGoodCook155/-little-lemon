package com.course.capstone.littlelemon.network.dto

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class MenuNetworkData(

 @SerialName("menu")
 val menu: List<Meal>

)
