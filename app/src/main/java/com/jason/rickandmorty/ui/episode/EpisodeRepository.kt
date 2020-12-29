package com.jason.rickandmorty.ui.episode

import android.util.Log
import com.jason.rickandmorty.data.database.EpisodeDao
import com.jason.rickandmorty.data.database.PageManager
import com.jason.rickandmorty.data.model.Episode
import com.jason.rickandmorty.data.model.GetAllEpisode
import com.jason.rickandmorty.data.network.CallCenter
import com.jason.rickandmorty.data.network.message_get_all_episode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

const val episode_page_key = "episode_page"

class EpisodeRepository @Inject constructor(private val callCenter: CallCenter,
                                            private val episodeDao: EpisodeDao,
                                            private val pageManager: PageManager) : CallCenter.APICallBack {
    var repoCallBack: RepoCallBack? = null
    private var totalPage = 4

    interface RepoCallBack {
        fun onDataBaseChange()
        fun onLastPage()
    }

    fun readEpisode(): List<Episode> {
        return episodeDao.readAll()
    }

    fun loadEpisode() {
        val page = pageManager.getPage(episode_page_key)
        if (page == totalPage) {
            repoCallBack?.onLastPage()
        }
        Log.d("abc", "load episode from page $page")
        callCenter.getAllEpisode(this, page)
    }

    override fun notify(message: String?, response: Any?) {
        when (message) {
            message_get_all_episode -> {
                (response as? GetAllEpisode)?.let {
                    saveEpisode(it.results)
                    pageManager.incrementPage(episode_page_key)
                    totalPage = it.info.pages

                }
            }
            else                    -> {
                Log.d("abc", "message $message response $response")
            }
        }
    }

    private fun saveEpisode(episodeList: List<Episode>) {
        CoroutineScope(Dispatchers.IO).launch {
            episodeDao.insertAll(episodeList)
            repoCallBack?.onDataBaseChange()
        }
    }
}