package com.jason.rickandmorty.ui.character

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.jason.rickandmorty.R
import com.jason.rickandmorty.data.model.Location
import com.jason.rickandmorty.databinding.DialogLocationBinding

class LocationFragment: DialogFragment() {
    var location: Location? = null
        set(value) {
            field = value
            init()
        }
    private var binding: DialogLocationBinding? = null

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.dialog_location, container)
        binding = DialogLocationBinding.bind(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    @SuppressLint("SetTextI18n")
    private fun init() {
        val dimension = location!!.dimension
        val name = location!!.name
        binding?.textDimension?.text = "Dimension: $dimension"
        binding?.textName?.text = "Name: $name"
        if (name.contains(dimension)){
            binding?.textDimension?.visibility = View.INVISIBLE
        }

    }
}