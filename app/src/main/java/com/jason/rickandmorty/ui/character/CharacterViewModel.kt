package com.jason.rickandmorty.ui.character

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jason.rickandmorty.data.model.Character
import com.jason.rickandmorty.data.model.Episode
import com.jason.rickandmorty.data.model.Location
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CharacterViewModel : ViewModel(), CharacterRepository.RepoCallBack {
    val characters = MutableLiveData<List<Character>>()
    val temp_location = MutableLiveData<Location?>()
    val episode_character = MutableLiveData<List<Character>>()
    val characterRepository = CharacterRepository().apply {
        repoCallBack = this@CharacterViewModel
    }
    val isLastPage = MutableLiveData<Boolean>().apply {
        value = false
    }
    init {
        characters.value = arrayListOf()
        temp_location.value = null
        episode_character.value = null
        readCharacter()
    }

    private fun readCharacter(){
        viewModelScope.launch{
            var characterList = listOf<Character>()
            withContext(Dispatchers.IO){
                characterList = characterRepository.readCharacter()
            }
            characters.value = characterList
        }
    }

    fun loadCharacter(){
        CoroutineScope(Dispatchers.IO).launch{
            characterRepository.loadCharacter()
        }
    }

    fun getEpisodeCharacter(episode: Episode){
        CoroutineScope(Dispatchers.IO).launch{
            characterRepository.getEpisodeCharacter(episode)
        }
    }

    fun getCharacterLocation(character: Character){
        CoroutineScope(Dispatchers.IO).launch{
            characterRepository.getCharacterLocation(character)
        }
    }

    override fun onDataBaseChange() {
        readCharacter()
    }

    override fun onLastPage() {
        isLastPage.value = true
    }

    override fun onGetLocation(location: Location) {
        viewModelScope.launch {
            withContext(Dispatchers.Main){
                temp_location.value = location
            }
        }
    }

    override fun onGetEpisodeCharacter(episodeCharacterList: ArrayList<Character>) {
        Log.d("abc", "get ${episodeCharacterList.size} character for episode")
        viewModelScope.launch {
            withContext(Dispatchers.Main){
                episode_character.value = episodeCharacterList.toList()
                Log.d("abc", "current size ${episode_character.value!!.size} character for episode")
                characterRepository.episodeCharacterList.clear()
            }
        }
    }
}