package com.jason.rickandmorty.ui.character

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jason.rickandmorty.R
import com.jason.rickandmorty.data.model.Character
import com.jason.rickandmorty.databinding.SingleCharacterBinding
import com.squareup.picasso.Picasso

class CharacterAdapter(): RecyclerView.Adapter<CharacterAdapter.MyViewHolder>() {
    var mList: List<Character> = listOf()

    class MyViewHolder(itemView: View, var binding: SingleCharacterBinding): RecyclerView.ViewHolder(itemView) {
        fun bind(character: Character) {
            binding.textName.text = character.name
            binding.textSpecies.text = "Species: ${character.species}"
            binding.textStatus.text = "Status: ${character.status}"
            Picasso.get().load(character.image).resize(400, 400).centerCrop().into(binding.imageCharacter)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_character, parent, false)
        val binding = SingleCharacterBinding.bind(view)
        return MyViewHolder(view, binding)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun refreshDataList(newList: List<Character>) {
        mList = newList
        Log.d("abc", "refresh adapter size ${mList.size}")
        notifyDataSetChanged()
    }

}