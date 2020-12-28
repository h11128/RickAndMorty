package com.jason.rickandmorty.ui.character

import android.util.Log
import com.jason.rickandmorty.data.database.AppDataBase
import com.jason.rickandmorty.data.database.CharacterDao
import com.jason.rickandmorty.data.database.PageManager
import com.jason.rickandmorty.data.model.Character
import com.jason.rickandmorty.data.model.GetAllCharacter
import com.jason.rickandmorty.data.model.Location
import com.jason.rickandmorty.data.network.CallCenter
import com.jason.rickandmorty.data.network.message_get_all_character
import com.jason.rickandmorty.data.network.message_get_single_location
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

const val character_page_key = "character_page"
class CharacterRepository : CallCenter.APICallBack {
    private val characterDao: CharacterDao = AppDataBase.getInstance().characterDao()
    var repoCallBack: RepoCallBack? = null
    var total_page = 999
        set(value) {
            field = value
            if (PageManager.getPage(character_page_key) == value){
                repoCallBack?.onLastPage()
            }
        }
    interface RepoCallBack{
        fun onDataBaseChange()
        fun onLastPage()
        fun onGetLocation(location: Location)
    }

    fun readCharacter(): List<Character> {
        return characterDao.readAll()
    }

    fun loadCharacter() {
        val page = PageManager.getPage(character_page_key)
        CallCenter.getAllCharacter(this, page)
    }

    override fun notify(message: String?, response: Any?) {
        when (message){
            message_get_all_character -> {
                (response as? GetAllCharacter)?.let {
                    saveCharacter(it.results)
                    PageManager.incrementPage(character_page_key)
                    total_page = it.info.pages

                }
            }
            message_get_single_location -> {
                (response as? Location)?.let {
                    repoCallBack?.onGetLocation(it)
                }
            }
            else -> {
                Log.d("abc", "message $message reponse $response")
            }
        }
    }

    private fun saveCharacter(characterList: List<Character>){
        CoroutineScope(Dispatchers.IO).launch {
            characterDao.insertAll(characterList)
            repoCallBack?.onDataBaseChange()
        }
    }

    fun getCharacterLocation(character: Character) {
        character.getLocationId()?.let{
            CallCenter.getSingleLocation(this, it)
        }
    }
}