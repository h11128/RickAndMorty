package com.jason.rickandmorty.data.network

import retrofit2.Retrofit

const val baseUrl = "https://rickandmortyapi.com/api"
const val endpoint_all_character = "/character"
const val endpoint_single_character = "/character/{id}"
const val endpoint_multiple_character = "/character/{id_array}"
const val endpoint_all_episode = "/episode"
const val endpoint_single_episode = "/episode/{id}"
const val endpoint_multiple_episode = "/episode/{id_array}"
const val endpoint_all_location = "/location"
const val endpoint_single_location = "/location/{id}"
const val endpoint_multiple_location = "/location/{id_array}"


interface RickAndMortyAPI {

    companion object{
        operator fun invoke(): RickAndMortyAPI{
            return Retrofit.Builder().baseUrl()
        }
    }

}