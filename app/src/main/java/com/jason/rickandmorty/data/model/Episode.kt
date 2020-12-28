package com.jason.rickandmorty.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Episode(val air_date: String,
                   val characters: List<String>,
                   val created: String,
                   val episode: String,
                   @PrimaryKey val id: Int,
                   val name: String,
                   val url: String): Serializable{

    fun getTitle(): String{
        return "$episode: $name "
    }
}