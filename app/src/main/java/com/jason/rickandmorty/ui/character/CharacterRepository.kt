package com.jason.rickandmorty.ui.character

import android.util.Log
import com.jason.rickandmorty.data.database.CharacterDao
import com.jason.rickandmorty.data.database.LocationDao
import com.jason.rickandmorty.data.database.PageManager
import com.jason.rickandmorty.data.model.*
import com.jason.rickandmorty.data.network.CallCenter
import com.jason.rickandmorty.data.network.message_get_all_character
import com.jason.rickandmorty.data.network.message_get_multiple_character
import com.jason.rickandmorty.data.network.message_get_single_location
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

const val character_page_key = "character_page"

class CharacterRepository @Inject constructor(private val characterDao: CharacterDao,
                                              private val locationDao: LocationDao,
                                              private val callCenter: CallCenter,
                                              private val pageManager: PageManager) :
    CallCenter.APICallBack {
    var repoCallBack: RepoCallBack? = null
    var episodeCharacterList = ArrayList<Character>()
    private var totalPage = 999
        set(value) {
            field = value
            if (pageManager.getPage(character_page_key) == value) {
                repoCallBack?.onLastPage()
            }
        }

    interface RepoCallBack {
        fun onDataBaseChange()
        fun onLastPage()
        fun onGetLocation(location: Location)
        fun onGetEpisodeCharacter(episodeCharacterList: ArrayList<Character>)
    }

    fun readCharacter(): List<Character> {
        return characterDao.readAll()
    }

    fun loadCharacter() {
        val page = pageManager.getPage(character_page_key)
        callCenter.getAllCharacter(this, page)
    }

    override fun notify(message: String?, response: Any?) {
        Log.d("abc", "message $message reponse $response")
        when (message) {

            message_get_all_character -> {
                (response as? GetAllCharacter)?.let {
                    saveCharacter(it.results)
                    pageManager.incrementPage(character_page_key)
                    totalPage = it.info.pages

                }
            }
            message_get_single_location -> {
                (response as? Location)?.let {

                    saveLocation(it)
                    repoCallBack?.onGetLocation(it)
                }
            }
            message_get_multiple_character -> {
                (response as? GetMultipleCharacter)?.let {
                    episodeCharacterList.addAll(it)
                    saveCharacter(it)

                    repoCallBack?.onGetEpisodeCharacter(episodeCharacterList)

                }
            }

            else                           -> {
                //Log.d("abc", "message $message reponse $response")
            }
        }
    }

    private fun saveCharacter(characterList: List<Character>) {
        CoroutineScope(Dispatchers.IO).launch {
            characterDao.insertAll(characterList)
            repoCallBack?.onDataBaseChange()
        }
    }

    private fun saveLocation(location: Location) {
        CoroutineScope(Dispatchers.IO).launch {
            locationDao.insert(location)
        }

    }


    fun getCharacterLocation(character: Character) {
        val location = locationDao.find(character.location.url)
        if (location.isNotEmpty()) {
            repoCallBack?.onGetLocation(location[0])
        } else {
            character.getLocationIdFromUrl()?.let {
                callCenter.getSingleLocation(this, it)
            }
        }
    }

    fun getEpisodeCharacter(episode: Episode) {
        var notLocalCharacterIDString = ""
        for (url in episode.characters) {
            val search_result = characterDao.find(url)
            if (search_result.isNotEmpty()) {
                episodeCharacterList.add(search_result[0])
            } else {
                notLocalCharacterIDString += "${Character.getIdFromUrl(url)},"
            }
        }
        if (notLocalCharacterIDString.isEmpty()) {
            repoCallBack?.onGetEpisodeCharacter(episodeCharacterList)

        } else {
            callCenter.getMultipleCharacter(this, notLocalCharacterIDString)
        }
        Log.d("abc", "get multiple character string $notLocalCharacterIDString")

    }
}