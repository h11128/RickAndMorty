package com.jason.rickandmorty.data.model


data class Info(val count: Int,
                val next: String,
                val pages: Int,
                val prev: Any)

data class GetAllCharacter(val info: Info, val results: List<Character>)

// GET single Character response: Character class

class GetMultipleCharacter : ArrayList<Character>()

data class GetAllLocation(val info: Info, val results: List<CharacterLocation>)

// GET single Location response: Location class

class GetMultipleLocation : ArrayList<CharacterLocation>()

data class GetAllEpisode(val info: Info, val results: List<Episode>)

// GET single Episode response: Episode class

class GetMultipleEpisode : ArrayList<Episode>()
