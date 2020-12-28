package com.jason.rickandmorty.data.network

import com.jason.rickandmorty.data.model.*

import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val error_string = "error"
const val message_get_all_character = "get_all_character"
const val message_get_single_character = "get_single_character"
const val message_get_multiple_character = "get_multiple_character"
const val message_get_all_episode = "get_all_episode"
const val message_get_single_episode = "get_single_episode"
const val message_get_multiple_episode = "get_multiple_episode"
const val message_get_all_location = "get_all_location"
const val message_get_single_location = "get_single_location"
const val message_get_multiple_location = "get_multiple_location"
class CallCenter {

    interface APICallBack {
        fun notify(message: String? = null, response: Any? = null)
    }


    companion object {
        val api = RickAndMortyAPI()

        fun onFailureMessage(t: Throwable): String{
            return "cause ${t.cause} message ${t.message}"
        }

        fun getAllCharacter(callback: APICallBack, page: Int) {
            val messageCode = message_get_all_character
            api.getAllCharacter(page).enqueue(object : Callback<GetAllCharacter> {
                override fun onResponse(call: Call<GetAllCharacter>,
                                        response: Response<GetAllCharacter>) {
                    if (response.isSuccessful) {
                        callback.notify(messageCode, response.body()!!)
                    } else {
                        val jObjError = JSONObject((response.errorBody() ?: return).string())
                        val error = jObjError.getString(error_string)
                        callback.notify(error, null)
                    }
                }

                override fun onFailure(call: Call<GetAllCharacter>, t: Throwable) {
                    callback.notify(onFailureMessage(t), null)
                }
            })
        }

        fun getSingleCharacter(callback: APICallBack, id: String){
            val messageCode = message_get_single_character
            api.getSingleCharacter(id).enqueue(object : Callback<Character> {
                override fun onResponse(call: Call<Character>,
                                        response: Response<Character>) {
                    if (response.isSuccessful) {
                        callback.notify(messageCode, response.body()!!)
                    } else {
                        val jObjError = JSONObject((response.errorBody() ?: return).string())
                        val error = jObjError.getString(error_string)
                        callback.notify(error, null)
                    }
                }

                override fun onFailure(call: Call<Character>, t: Throwable) {
                    callback.notify(onFailureMessage(t), null)
                }
            })
        }
        
        fun getMultipleCharacter(callback: APICallBack, idArray: String){
            val messageCode = message_get_multiple_character
            api.getMultipleCharacter(idArray).enqueue(object: Callback<GetMultipleCharacter>{
                override fun onResponse(call: Call<GetMultipleCharacter>,
                                        response: Response<GetMultipleCharacter>) {
                    if (response.isSuccessful) {
                        callback.notify(messageCode, response.body()!!)
                    } else {
                        val jObjError = JSONObject((response.errorBody() ?: return).string())
                        val error = jObjError.getString(error_string)
                        callback.notify(error, null)
                    }
                }

                override fun onFailure(call: Call<GetMultipleCharacter>, t: Throwable) {
                    callback.notify(onFailureMessage(t), null)
                }
            })
        }


        fun getAllEpisode(callback: APICallBack, page: Int) {
            val messageCode = message_get_all_episode
            api.getAllEpisode(page).enqueue(object : Callback<GetAllEpisode> {
                override fun onResponse(call: Call<GetAllEpisode>,
                                        response: Response<GetAllEpisode>) {
                    if (response.isSuccessful) {
                        callback.notify(messageCode, response.body()!!)
                    } else {
                        val jObjError = JSONObject((response.errorBody() ?: return).string())
                        val error = jObjError.getString(error_string)
                        callback.notify(error, null)
                    }
                }

                override fun onFailure(call: Call<GetAllEpisode>, t: Throwable) {
                    callback.notify(onFailureMessage(t), null)
                }
            })
        }

        fun getSingleEpisode(callback: APICallBack, id: String){
            val messageCode = message_get_single_episode
            api.getSingleEpisode(id).enqueue(object : Callback<Episode> {
                override fun onResponse(call: Call<Episode>,
                                        response: Response<Episode>) {
                    if (response.isSuccessful) {
                        callback.notify(messageCode, response.body()!!)
                    } else {
                        val jObjError = JSONObject((response.errorBody() ?: return).string())
                        val error = jObjError.getString(error_string)
                        callback.notify(error, null)
                    }
                }

                override fun onFailure(call: Call<Episode>, t: Throwable) {
                    callback.notify(onFailureMessage(t), null)
                }
            })
        }

        fun getMultipleEpisode(callback: APICallBack, idArray: String){
            val messageCode = message_get_multiple_episode
            api.getMultipleEpisode(idArray).enqueue(object: Callback<GetMultipleEpisode>{
                override fun onResponse(call: Call<GetMultipleEpisode>,
                                        response: Response<GetMultipleEpisode>) {
                    if (response.isSuccessful) {
                        callback.notify(messageCode, response.body()!!)
                    } else {
                        val jObjError = JSONObject((response.errorBody() ?: return).string())
                        val error = jObjError.getString(error_string)
                        callback.notify(error, null)
                    }
                }

                override fun onFailure(call: Call<GetMultipleEpisode>, t: Throwable) {
                    callback.notify(onFailureMessage(t), null)
                }
            })
        }

        fun getAllLocation(callback: APICallBack, page: Int) {
            val messageCode = message_get_all_location
            api.getAllLocation(page).enqueue(object : Callback<GetAllLocation> {
                override fun onResponse(call: Call<GetAllLocation>,
                                        response: Response<GetAllLocation>) {
                    if (response.isSuccessful) {
                        callback.notify(messageCode, response.body()!!)
                    } else {
                        val jObjError = JSONObject((response.errorBody() ?: return).string())
                        val error = jObjError.getString(error_string)
                        callback.notify(error, null)
                    }
                }

                override fun onFailure(call: Call<GetAllLocation>, t: Throwable) {
                    callback.notify(onFailureMessage(t), null)
                }
            })
        }

        fun getSingleLocation(callback: APICallBack, id: String){
            val messageCode = message_get_single_location
            api.getSingleLocation(id).enqueue(object : Callback<Location> {
                override fun onResponse(call: Call<Location>,
                                        response: Response<Location>) {
                    if (response.isSuccessful) {
                        callback.notify(messageCode, response.body()!!)
                    } else {
                        val jObjError = JSONObject((response.errorBody() ?: return).string())
                        val error = jObjError.getString(error_string)
                        callback.notify(error, null)
                    }
                }

                override fun onFailure(call: Call<Location>, t: Throwable) {
                    callback.notify(onFailureMessage(t), null)
                }
            })
        }

        fun getMultipleLocation(callback: APICallBack, idArray: String){
            val messageCode = message_get_multiple_location
            api.getMultipleLocation(idArray).enqueue(object: Callback<GetMultipleLocation>{
                override fun onResponse(call: Call<GetMultipleLocation>,
                                        response: Response<GetMultipleLocation>) {
                    if (response.isSuccessful) {
                        callback.notify(messageCode, response.body()!!)
                    } else {
                        val jObjError = JSONObject((response.errorBody() ?: return).string())
                        val error = jObjError.getString(error_string)
                        callback.notify(error, null)
                    }
                }

                override fun onFailure(call: Call<GetMultipleLocation>, t: Throwable) {
                    callback.notify(onFailureMessage(t), null)
                }
            })
        }

    }


}