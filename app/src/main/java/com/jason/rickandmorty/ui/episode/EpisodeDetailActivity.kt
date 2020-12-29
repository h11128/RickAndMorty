package com.jason.rickandmorty.ui.episode

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.jason.rickandmorty.data.model.Episode
import com.jason.rickandmorty.databinding.ActivityEpisodeDetailBinding
import com.jason.rickandmorty.ui.character.CharacterAdapter
import com.jason.rickandmorty.ui.character.CharacterViewModel

class EpisodeDetailActivity : AppCompatActivity() {
    private var binding: ActivityEpisodeDetailBinding? = null
    private val viewAdapter = CharacterAdapter()
    private lateinit var viewModel: CharacterViewModel
    private lateinit var episode: Episode
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEpisodeDetailBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        episode = intent.getSerializableExtra("episode") as Episode
        title = "Episode Detail"
        init()
    }

    @SuppressLint("SetTextI18n")
    private fun init() {
        viewModel = ViewModelProvider(this).get(CharacterViewModel::class.java)
        viewModel.episodeCharacter.observe(this, { characterList ->
            if (characterList != null){
                Log.d("abc", "observe episode character ${characterList.size}")
                viewAdapter.refreshDataList(characterList)

            }
        })



        binding!!.recyclerEpisodeCharacter.apply {
            adapter = viewAdapter
            layoutManager = GridLayoutManager(this@EpisodeDetailActivity,2)
        }
        episode.let {
            binding?.let { binding ->
                binding.textAirDate.text = "Air Date: ${it.air_date}"
                binding.textEpisode.text = "Episode: ${it.episode}"
                binding.textName.text = "Name: ${it.name}"
                binding.textUrl.text = "Url: ${it.url}"

            }
        }

        Handler().postDelayed({
            viewModel.getEpisodeCharacter(episode)
        }, 1500)
    }

}