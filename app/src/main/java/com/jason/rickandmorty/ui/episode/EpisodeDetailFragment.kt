package com.jason.rickandmorty.ui.episode

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.jason.rickandmorty.R
import com.jason.rickandmorty.data.model.Episode
import com.jason.rickandmorty.databinding.FragmentEpisodeDetailBinding
import com.jason.rickandmorty.ui.MyApplication
import com.jason.rickandmorty.ui.character.CharacterAdapter
import com.jason.rickandmorty.ui.character.CharacterViewModel
import javax.inject.Inject


class EpisodeDetailFragment(private var episode: Episode) : Fragment() {


    private var binding: FragmentEpisodeDetailBinding? = null
    private val viewAdapter = CharacterAdapter()
    @Inject
    lateinit var viewModel: CharacterViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        MyApplication.appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_episode_detail, container, false)
        binding = FragmentEpisodeDetailBinding.bind(view)
        //viewModel = ViewModelProvider(this).get(CharacterViewModel::class.java)
        viewModel.episodeCharacter.observe(viewLifecycleOwner, { characterList ->
            if (characterList != null){
                Log.d("abc", "observe episode character ${characterList.size}")
                viewAdapter.refreshDataList(characterList)

            }
        })

        viewModel.getEpisodeCharacter(episode)

        binding!!.recyclerEpisodeCharacter.apply {
            adapter = viewAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("abc" , "onViewCreated")
        init()
    }

    @SuppressLint("SetTextI18n")
    private fun init() {
        episode.let {
            binding?.let { binding ->
                binding.textAirDate.text = "Air Date: ${it.air_date}"
                binding.textEpisode.text = "Episode: ${it.episode}"
                binding.textName.text = "Name: ${it.name}"
                binding.textUrl.text = "Url: ${it.url}"

            }
        }
    }

/*    companion object {

        @JvmStatic
        fun newInstance(episode: Episode) = EpisodeDetailFragment(episode)
    }*/
}