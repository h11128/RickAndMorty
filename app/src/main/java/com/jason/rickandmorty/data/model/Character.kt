package com.jason.rickandmorty.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

// GET single Character
@Entity
data class Character(val created: String,
                     val episode: List<String>,
                     val gender: String,
                     @PrimaryKey val id: Int,
                     val image: String,
                     val location: SimpleLocation,
                     val name: String,
                     val origin: Origin,
                     val species: String,
                     val status: String,
                     val type: String,
                     val url: String) {
    fun getLocationId(): String?{
        val url = location.url
        val strings = url.split("/")
        return if (strings.isNotEmpty()){
            strings[strings.lastIndex].toIntOrNull().toString()
        } else{
            null
        }
    }




}

data class SimpleLocation(val name: String, val url: String)

data class Origin(val name: String, val url: String)