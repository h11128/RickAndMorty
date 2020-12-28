package com.jason.rickandmorty.ui.character

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
            layoutManager = LinearLayoutManager(requireContext())
        }

        viewModel.characters.observe(viewLifecycleOwner, {
            viewAdapter.refreshDataList(it)
        })

        viewModel.isLastPage.observe(viewLifecycleOwner, {
            if(it){
                binding.textHint.text = "this is the last page"
            }
        })
        SwipeDetector(binding.root).setOnSwipeListener(object :
            SwipeDetector.OnSwipeEvent {
            override fun swipeEventDetected(v: View?, swipeType: SwipeDetector.SwipeTypeEnum?) {
                if(swipeType==SwipeDetector.SwipeTypeEnum.BOTTOM_TO_TOP)
                    viewModel.loadCharacter()
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