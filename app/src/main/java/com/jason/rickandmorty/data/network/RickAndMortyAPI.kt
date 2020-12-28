package com.jason.rickandmorty.data.network

import com.jason.rickandmorty.data.model.*
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val baseUrl = "https://rickandmortyapi.com/api/"
const val endpoint_all_character = "character/"
const val endpoint_single_character = "character/{id}"
const val endpoint_multiple_character = "character/{id_array}"
const val endpoint_all_episode = "episode"
const val endpoint_single_episode = "episode/{id}"
const val endpoint_multiple_episode = "episode/{id_array}"
const val endpoint_all_location = "location"
const val endpoint_single_location = "location/{id}"
const val endpoint_multiple_location = "location/{id_array}"


interface RickAndMortyAPI {

    companion object {
        operator fun invoke(): RickAndMortyAPI {
            return Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(RickAndMortyAPI::class.java)
        }
    }

    @GET(endpoint_all_character)
    fun getAllCharacter(@Query("page") page: Int): Call<GetAllCharacter>

    @GET(endpoint_all_character)
    fun getNextPageCharacter(@Path("endpoint") endpoint: String): Call<GetAllCharacter>

    @GET(endpoint_single_character)
    fun getSingleCharacter(@Path("id") id: String): Call<Character>

    @GET(endpoint_multiple_character)
    fun getMultipleCharacter(@Path("id_array") idArray: String): Call<GetMultipleCharacter>

    @GET(endpoint_all_episode)
    fun getAllEpisode(@Query("page") page: Int): Call<GetAllEpisode>

    @GET(endpoint_single_episode)
    fun getSingleEpisode(@Path("id") id: String): Call<Episode>

    @GET(endpoint_multiple_episode)
    fun getMultipleEpisode(@Path("id_array") idArray: String): Call<GetMultipleEpisode>

    @GET(endpoint_all_location)
    fun getAllLocation(@Query("page") page: Int): Call<GetAllLocation>

    @GET(endpoint_single_location)
    fun getSingleLocation(@Path("id") id: String): Call<Location>

    @GET(endpoint_multiple_location)
    fun getMultipleLocation(@Path("id_array") idArray: String): Call<GetMultipleLocation>

}