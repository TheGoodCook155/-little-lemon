package com.course.capstone.littlelemon.datastore

import androidx.datastore.core.Serializer
import com.course.capstone.littlelemon.model.User
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream


object AppSerializer: Serializer<StoreValues>{

    override val defaultValue: StoreValues
        get() = StoreValues()

    override suspend fun readFrom(input: InputStream): StoreValues {

        return try {

            Json.decodeFromString(
                deserializer = StoreValues.serializer(),
                string = input.readBytes().decodeToString()
            )

        }catch (e: SerializationException){
            e.printStackTrace()
            defaultValue
        }

    }

    override suspend fun writeTo(t: StoreValues, output: OutputStream) {
        output.write(
            Json.encodeToString(
                serializer = StoreValues.serializer(),
                value = t
            ).encodeToByteArray()
        )
    }


}



