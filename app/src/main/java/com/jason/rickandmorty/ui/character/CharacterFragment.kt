package com.jason.rickandmorty.ui.character

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.jason.rickandmorty.R
import com.jason.rickandmorty.data.model.Character
import com.jason.rickandmorty.databinding.FragmentCharacterBinding
import com.jason.rickandmorty.ui.MyApplication
import com.jason.rickandmorty.ui.helper.SwipeDetector
import javax.inject.Inject

class CharacterFragment : Fragment() {
    companion object;

    override fun onAttach(context: Context) {
        super.onAttach(context)
        MyApplication.appComponent.inject(this)
        Log.d("CharacterFragment", "OnAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("CharacterFragment", "OnCreate")

    }

    @Inject
    lateinit var viewModel: CharacterViewModel
    private lateinit var binding: FragmentCharacterBinding
    private val viewAdapter = CharacterAdapter().apply {
        parentFragment = this@CharacterFragment
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_character, container, false)
        binding = FragmentCharacterBinding.bind(view)
        Log.d("CharacterFragment", "OnCreateView")

        return view
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d("CharacterFragment", "OnActivityCreated")

        //viewModel = ViewModelProvider(this).get(CharacterViewModel::class.java)
        binding.recyclerCharacter.apply {
            adapter = viewAdapter
            layoutManager =
                if (binding.root.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
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
        viewModel.tempLocation.observe(viewLifecycleOwner, {
            if (it != null) {
                val dialogFragment = LocationFragment().apply {
                    location = it
                }
                dialogFragment.showNow(requireActivity().supportFragmentManager, "Location dialog")
            }
        })
    }

    override fun onStart() {
        super.onStart()
        Log.d("CharacterFragment", "OnStart")

    }


    override fun onPause() {
        super.onPause()
        Log.d("CharacterFragment", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("CharacterFragment", "OnStop")

    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("CharacterFragment", "OnDestroyView")

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("CharacterFragment", "OnDestroy")

    }

    override fun onDetach() {
        super.onDetach()
        Log.d("CharacterFragment", "OnDetach")
    }

    override fun onResume() {
        super.onResume()
        Log.d("CharacterFragment", "OnResume")
        viewModel.tempLocation.value = null
    }

    fun onCharacterClick(character: Character) {
        viewModel.getCharacterLocation(character)
    }

}