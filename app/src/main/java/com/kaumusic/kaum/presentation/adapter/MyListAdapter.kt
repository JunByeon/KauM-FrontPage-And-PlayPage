package com.kaumusic.kaum.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.kaumusic.kaum.R
import com.kaumusic.kaum.databinding.ListLinearBinding
import com.kaumusic.kaum.domain.Music
import com.kaumusic.kaum.domain.Song

class MyListAdapter(val musiclist: LiveData<MutableList<Song>>) : RecyclerView.Adapter<MyListAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ListLinearBinding.inflate(LayoutInflater.from(parent.context))
        return Holder(binding)
    }

    override fun getItemCount() = musiclist.value?.size ?: 0

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(musiclist.value?.getOrNull(position))
    }

    class Holder(private val binding: ListLinearBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(musiclist : Song?){
            musiclist?.let{music ->
                binding.run{
                    imageView.setImageResource(music.coverImg ?: R.drawable.baseline_album_24)
                    txtTitle.text = music.title
                    txtSinger.text = music.singer
                }
                binding.root.setOnClickListener{
                    Toast.makeText(
                        binding.root.context,
                        "${music.title} - ${music.singer} 가 재생됩니다!",
                        Toast.LENGTH_SHORT)
                        .show()
                }
            }

        }
    }
}