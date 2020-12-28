package com.jason.rickandmorty.ui.episode

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.jason.rickandmorty.R
import com.jason.rickandmorty.data.model.Episode
import com.jason.rickandmorty.databinding.FragmentEpisodeBinding
import com.jason.rickandmorty.ui.character.CharacterViewModel
import com.jason.rickandmorty.ui.helper.SwipeDetector

class EpisodeFragment : Fragment() {

    companion object {
        fun newInstance() = EpisodeFragment()
    }

    private lateinit var viewModel: EpisodeViewModel
    private lateinit var binding: FragmentEpisodeBinding
    private val viewAdapter = EpisodeAdapter().apply {
        parentFragment = this@EpisodeFragment
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_episode, container, false)
        binding = FragmentEpisodeBinding.bind(view)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(EpisodeViewModel::class.java)
        viewModel.isLastPage.observe(viewLifecycleOwner, {
            if (it) {
                binding.textHint.text = "this is the last page"
            }
        })
        binding.recyclerEpisode.apply {
            adapter = viewAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        viewModel.episodes.observe(viewLifecycleOwner, {
            viewAdapter.refreshDataList(it)
        })
        SwipeDetector(binding.root).setOnSwipeListener(object :
            SwipeDetector.OnSwipeEvent {
            override fun swipeEventDetected(v: View?, swipeType: SwipeDetector.SwipeTypeEnum?) {
                if (swipeType == SwipeDetector.SwipeTypeEnum.BOTTOM_TO_TOP)
                    viewModel.loadEpisode()
            }
        })
    }

    fun onEpisodeClick(episode: Episode) {
        val intent = Intent(requireActivity(), EpisodeDetailActivity::class.java)
        intent.putExtra("episode", episode)
        startActivity(intent)

    }

}