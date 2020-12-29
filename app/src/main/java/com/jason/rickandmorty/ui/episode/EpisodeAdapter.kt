package com.jason.rickandmorty.ui.episode

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jason.rickandmorty.R
import com.jason.rickandmorty.data.model.Episode
import com.jason.rickandmorty.databinding.SingleEpisodeBinding

class EpisodeAdapter : RecyclerView.Adapter<EpisodeAdapter.MyViewHolder>() {
    private var mList = listOf<Episode>()
    var parentFragment: EpisodeFragment? = null
    class MyViewHolder(itemView: View, private var binding: SingleEpisodeBinding, private var parentFragment: EpisodeFragment?): RecyclerView.ViewHolder(itemView) {
        fun bind(episode: Episode) {
            binding.textDate.text = episode.air_date
            binding.textTitle.text = episode.getTitle()
            binding.root.setOnClickListener {
                parentFragment?.onEpisodeClick(episode)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_episode, parent, false)
        val binding = SingleEpisodeBinding.bind(view)
        return MyViewHolder(view, binding, parentFragment)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun refreshDataList(newList: List<Episode>) {
        mList = newList
        Log.d("abc", "refresh adapter size ${mList.size}")
        notifyDataSetChanged()
    }
}