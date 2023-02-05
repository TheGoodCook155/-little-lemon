package com.course.capstone.littlelemon.model

import androidx.datastore.core.DataStore
import com.course.capstone.littlelemon.datastore.StoreValues

@kotlinx.serialization.Serializable
class User(
    var firstName: String,
    var lastName: String,
    var emailAddress: String
){

    override fun toString(): String {
        return "User(firstName='$firstName', lastName='$lastName', emailAddress='$emailAddress')"
    }


    suspend fun insertUser(
        dataStore: DataStore<StoreValues>,
        user: User
    ){

        dataStore.updateData {
            it.copy(
                user = user
            )
        }

    }

    suspend fun removeUser(
        dataStore: DataStore<StoreValues>,
    ){

        dataStore.updateData {
            it.copy(
                user = User("","","")
            )
        }

    }


}
