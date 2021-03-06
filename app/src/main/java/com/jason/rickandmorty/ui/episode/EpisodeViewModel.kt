package com.jason.rickandmorty.ui.episode

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jason.rickandmorty.data.model.Episode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class EpisodeViewModel @Inject constructor(private val episodeRepository: EpisodeRepository) : ViewModel(), EpisodeRepository.RepoCallBack {
    val episodes = MutableLiveData<List<Episode>>()
    val isLastPage = MutableLiveData<Boolean>().apply {
        value = false
    }
    init {
        episodeRepository.repoCallBack = this
        episodes.value = arrayListOf()
        readEpisode()
    }

    private fun readEpisode(){
        CoroutineScope(Dispatchers.IO).launch{
            episodes.postValue(episodeRepository.readEpisode())
        }
    }

    fun loadEpisode(){
        CoroutineScope(Dispatchers.IO).launch{
            episodeRepository.loadEpisode()
        }
    }

    override fun onDataBaseChange() {
        readEpisode()
    }

    override fun onLastPage() {
        CoroutineScope(Dispatchers.Main).launch{
            isLastPage.value = true
        }

    }
}