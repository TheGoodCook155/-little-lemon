package com.course.capstone.littlelemon.datastore

import com.course.capstone.littlelemon.model.User

@kotlinx.serialization.Serializable
data class StoreValues(
    val user: User = User("","","")
)







