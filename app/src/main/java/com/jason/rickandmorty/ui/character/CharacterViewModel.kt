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
import javax.inject.Inject

class CharacterViewModel @Inject constructor(private val characterRepository: CharacterRepository) :
    ViewModel(), CharacterRepository.RepoCallBack {
    val characters = MutableLiveData<List<Character>>()
    val tempLocation = MutableLiveData<Location?>()
    val episodeCharacter = MutableLiveData<List<Character>>()
    val isLastPage = MutableLiveData<Boolean>().apply {
        value = false
    }

    init {
        characterRepository.repoCallBack = this@CharacterViewModel
        characters.value = arrayListOf()
        tempLocation.value = null
        episodeCharacter.value = null
        readCharacter()
    }

    private fun readCharacter() {
        viewModelScope.launch {
            var characterList = listOf<Character>()
            withContext(Dispatchers.IO) {
                characterList = characterRepository.readCharacter()
            }
            characters.value = characterList
        }
    }

    fun loadCharacter() {
        CoroutineScope(Dispatchers.IO).launch {
            characterRepository.loadCharacter()
        }
    }

    fun getEpisodeCharacter(episode: Episode) {
        CoroutineScope(Dispatchers.IO).launch {
            characterRepository.getEpisodeCharacter(episode)
        }
    }

    fun getCharacterLocation(character: Character) {
        CoroutineScope(Dispatchers.IO).launch {
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
            withContext(Dispatchers.Main) {
                tempLocation.value = location
            }
        }
    }

    override fun onGetEpisodeCharacter(episodeCharacterList: ArrayList<Character>) {
        Log.d("abc", "get ${episodeCharacterList.size} character for episode")
        viewModelScope.launch {
            withContext(Dispatchers.Main) {
                episodeCharacter.value = episodeCharacterList.toList()
                Log.d("abc", "current size ${episodeCharacter.value!!.size} character for episode")
                characterRepository.episodeCharacterList.clear()
            }
        }
    }
}