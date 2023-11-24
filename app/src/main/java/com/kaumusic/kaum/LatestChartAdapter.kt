package com.example.androidmusicapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.kaumusic.kaum.Music
import com.kaumusic.kaum.R
import com.kaumusic.kaum.databinding.ListHorizontalBinding

class LatestChartAdapter(val latestChartlist: Array<Music>)
    : RecyclerView.Adapter<LatestChartAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ListHorizontalBinding.inflate(LayoutInflater.from(parent.context))
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(latestChartlist[position])
    }

    override fun getItemCount() = latestChartlist.size

    class Holder(private val binding: ListHorizontalBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(latestChartlist : Music){
            binding.run{
                imgMusic.setImageResource(R.drawable.img_album_exp4)
                txtTitle.text = latestChartlist.title
                txtSinger.text = latestChartlist.singer
            }
            binding.root.setOnClickListener{
                it.findNavController().navigate(R.id.action_chartFragment_to_myListFragment)
            }
        }

    }
}