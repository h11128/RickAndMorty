package com.jason.rickandmorty.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Location(
        val created: String,
        val dimension: String,
        @PrimaryKey val id: Int,
        val name: String,
        val residents: List<String>,
        val type: String,
        val url: String)