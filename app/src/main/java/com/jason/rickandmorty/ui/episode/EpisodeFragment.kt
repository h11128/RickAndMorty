package com.jason.rickandmorty.ui.episode

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.jason.rickandmorty.R
import com.jason.rickandmorty.data.model.Episode
import com.jason.rickandmorty.databinding.FragmentEpisodeBinding
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
            if (binding.root.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                if (it) {
                    binding.textHint.text = " You \n Fetched \n All \n Pages"
                } else {
                    binding.textHint.text = " Swipe \n ← \n to \n get \n Episodes"
                }
            } else {
                if (it) {
                    binding.textHint.text = " You Fetched All Pages"
                } else {
                    binding.textHint.text = " Swipe ↑ to get Episodes"
                }
            }
        })
        binding.recyclerEpisode.apply {
            adapter = viewAdapter
            layoutManager = if (binding.root.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
            } else {
                LinearLayoutManager(requireContext())
            }
        }

        viewModel.episodes.observe(viewLifecycleOwner,
            {
                Log.d("abc", "observe ${it.size} episodes")
                viewAdapter.refreshDataList(it)
            })
        SwipeDetector(binding.root).setOnSwipeListener(
            object :
                SwipeDetector.OnSwipeEvent {
                override fun swipeEventDetected(v: View?, swipeType: SwipeDetector.SwipeTypeEnum?) {
                    if (binding.root.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                        if (swipeType == SwipeDetector.SwipeTypeEnum.BOTTOM_TO_TOP)
                            viewModel.loadEpisode()
                    } else {
                        if (swipeType == SwipeDetector.SwipeTypeEnum.RIGHT_TO_LEFT)
                            viewModel.loadEpisode()
                    }

                }
            })
    }

    fun onEpisodeClick(episode: Episode) {
        val intent = Intent(requireActivity(), EpisodeDetailActivity::class.java)
        intent.putExtra("episode", episode)
        startActivity(intent)

    }

}