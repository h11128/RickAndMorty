package com.jason.rickandmorty.data.model

// GET single Character
data class Character(val created: String,
                     val episode: List<String>,
                     val gender: String,
                     val id: Int,
                     val image: String,
                     val location: CharacterLocation,
                     val name: String,
                     val origin: Origin,
                     val species: String,
                     val status: String,
                     val type: String,
                     val url: String) {}

data class Location(val name: String, val url: String)

data class Origin(val name: String, val url: String)