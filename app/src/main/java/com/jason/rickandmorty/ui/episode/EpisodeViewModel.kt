package com.jason.rickandmorty.ui.episode

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jason.rickandmorty.data.model.Episode
import com.jason.rickandmorty.ui.episode.EpisodeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EpisodeViewModel : ViewModel(), EpisodeRepository.RepoCallBack {
    val episodes = MutableLiveData<List<Episode>>()
    val episodeRepository = EpisodeRepository().apply {
        repoCallBack = this@EpisodeViewModel
    }
    init {
        episodes.value = arrayListOf()
        readEpisode()
    }

    private fun readEpisode(){
        CoroutineScope(Dispatchers.IO).launch{
            episodes.value = episodeRepository.readEpisode()
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
        TODO("Not yet implemented")
    }
}