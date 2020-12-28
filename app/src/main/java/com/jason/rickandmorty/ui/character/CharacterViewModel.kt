package com.jason.rickandmorty.ui.character

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jason.rickandmorty.data.model.Character
import com.jason.rickandmorty.data.model.Location
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CharacterViewModel : ViewModel(), CharacterRepository.RepoCallBack {
    val characters = MutableLiveData<List<Character>>()
    val temp_location = MutableLiveData<Location?>()
    val characterRepository = CharacterRepository().apply {
        repoCallBack = this@CharacterViewModel
    }
    init {
        characters.value = arrayListOf()
        temp_location.value = null
        readCharacter()
    }

    private fun readCharacter(){
        CoroutineScope(Dispatchers.IO).launch{
            characters.value = characterRepository.readCharacter()
        }
    }

    fun loadCharacter(){
        CoroutineScope(Dispatchers.IO).launch{
            characterRepository.loadCharacter()
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
        TODO("Not yet implemented")
    }

    override fun onGetLocation(location: Location) {
        TODO("Not yet implemented")
    }
}