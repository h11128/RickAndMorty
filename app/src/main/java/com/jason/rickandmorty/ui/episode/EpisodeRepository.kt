package com.jason.rickandmorty.ui.episode

import android.util.Log
import com.jason.rickandmorty.data.database.AppDataBase
import com.jason.rickandmorty.data.database.EpisodeDao
import com.jason.rickandmorty.data.database.PageManager
import com.jason.rickandmorty.data.model.Character
import com.jason.rickandmorty.data.model.Episode
import com.jason.rickandmorty.data.model.GetAllCharacter
import com.jason.rickandmorty.data.model.GetAllEpisode
import com.jason.rickandmorty.data.network.CallCenter
import com.jason.rickandmorty.data.network.message_get_all_episode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

const val episode_page_key = "episode_page"
class EpisodeRepository : CallCenter.APICallBack {
    private val episodeDao: EpisodeDao = AppDataBase.getInstance().episodeDao()
    var repoCallBack: RepoCallBack? = null
    var total_page = 4
    interface RepoCallBack{
        fun onDataBaseChange()
        fun onLastPage()
    }

    fun readEpisode(): List<Episode> {
        return episodeDao.readAll()
    }

    fun loadEpisode() {
        val page = PageManager.getPage(episode_page_key)
        if (page == total_page){
            repoCallBack?.onLastPage()
        }
        Log.d("abc", "load episode from page $page")
        CallCenter.getAllEpisode(this, page)
    }

    override fun notify(message: String?, response: Any?) {
        when (message){
            message_get_all_episode -> {
                (response as? GetAllEpisode)?.let {
                    saveEpisode(it.results)
                    PageManager.incrementPage(episode_page_key)
                    total_page = it.info.pages

                }
            }
            else -> {
                Log.d("abc", "message $message reponse $response")
            }
        }
    }

    private fun saveEpisode(episodeList: List<Episode>){
        CoroutineScope(Dispatchers.IO).launch {
            episodeDao.insertAll(episodeList)
            repoCallBack?.onDataBaseChange()
        }
    }
}