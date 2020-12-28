package com.jason.rickandmorty.ui.character

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.jason.rickandmorty.R
import com.jason.rickandmorty.data.model.Character
import com.jason.rickandmorty.databinding.FragmentCharacterBinding
import com.jason.rickandmorty.ui.helper.SwipeDetector
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager

class CharacterFragment : Fragment() {

    companion object {
        fun newInstance() = CharacterFragment()
    }

    private lateinit var viewModel: CharacterViewModel
    private lateinit var binding: FragmentCharacterBinding
    private val viewAdapter = CharacterAdapter().apply {
        parentFragment = this@CharacterFragment
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_character, container, false)
        binding = FragmentCharacterBinding.bind(view)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CharacterViewModel::class.java)
        binding.recyclerCharacter.apply {
            adapter = viewAdapter
            layoutManager = if (binding.root.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
            } else {
                LinearLayoutManager(requireContext())
            }
        }

        viewModel.characters.observe(viewLifecycleOwner, {
            viewAdapter.refreshDataList(it)
        })

        viewModel.isLastPage.observe(viewLifecycleOwner, {
            if (binding.root.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                if (it) {
                    binding.textHint.text = " You \n Fetched \n All \n Pages"
                } else {
                    binding.textHint.text = " Swipe \n ← \n to \n get \n Characters"
                }
            } else {
                if (it) {
                    binding.textHint.text = " You Fetched All Pages"
                } else {
                    binding.textHint.text = " Swipe ↑ to get Characters"
                }
            }
        })
        SwipeDetector(binding.root).setOnSwipeListener(object :
            SwipeDetector.OnSwipeEvent {
            override fun swipeEventDetected(v: View?, swipeType: SwipeDetector.SwipeTypeEnum?) {
                if (binding.root.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                    if (swipeType == SwipeDetector.SwipeTypeEnum.BOTTOM_TO_TOP)
                        viewModel.loadCharacter()
                } else {
                    if (swipeType == SwipeDetector.SwipeTypeEnum.RIGHT_TO_LEFT)
                        viewModel.loadCharacter()
                }
            }
        })
        viewModel.temp_location.observe(viewLifecycleOwner, {
            if (it!= null){
                val dialogFragment = LocationFragment().apply {
                    location = it
                }
                dialogFragment.showNow(requireActivity().supportFragmentManager, "Location dialog")
            }
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.temp_location.value = null
    }

    fun onCharacterClick(character: Character){
        viewModel.getCharacterLocation(character)
    }

}