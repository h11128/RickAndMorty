package com.jason.rickandmorty.data.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson

import com.google.gson.reflect.TypeToken
import java.util.*


// GET single Character
@Entity
data class Character(val created: String,
                     val episode: List<String>,
                     val gender: String,
                     @PrimaryKey val id: Int,
                     val image: String,
                     @Embedded(prefix = "simpleLocation_") val location: SimpleLocation,
                     val name: String,
                     @Embedded(prefix = "origin_") val origin: Origin,
                     val species: String,
                     val status: String,
                     val type: String,
                     val url: String) {
    fun getLocationId(): String? {
        val url = location.url
        val strings = url.split("/")
        return if (strings.isNotEmpty()) {
            strings[strings.lastIndex].toIntOrNull().toString()
        } else {
            null
        }
    }
}

class Converters {
    @TypeConverter
    fun storedStringToMyObjects(data: String?): List<String>? {
        val gson = Gson()
        if (data == null) {
            return Collections.emptyList()
        }
        val listType = object : TypeToken<List<String>?>() {}.type
        return gson.fromJson<List<String>>(data, listType)
    }

    @TypeConverter
    fun myObjectsToStoredString(myObjects: List<String>?): String? {
        val gson = Gson()
        return gson.toJson(myObjects)
    }
}

data class SimpleLocation(val name: String, val url: String)

data class Origin(val name: String, val url: String)