package com.kaumusic.kaum.presentation.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.kaumusic.kaum.R
import com.kaumusic.kaum.databinding.ListLinearBinding
import com.kaumusic.kaum.domain.Song
import com.kaumusic.kaum.presentation.activity.SongActivity

class MyListAdapter(private val musiclist: LiveData<MutableList<Song>>) : RecyclerView.Adapter<MyListAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ListLinearBinding.inflate(LayoutInflater.from(parent.context))
        return Holder(binding)
    }

    override fun getItemCount() = musiclist.value?.size ?: 0

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(musiclist.value?.getOrNull(position))
    }

    class Holder(private val binding: ListLinearBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(music : Song?){
            music?.let{elem ->
                binding.run{
                    imageView.setImageResource(elem.coverImg ?: R.drawable.baseline_album_24)
                    txtTitle.text = elem.title
                    txtSinger.text = elem.singer
                }

                binding.root.setOnClickListener{
                    val intent = Intent(it.context, SongActivity::class.java)
                    startActivity(it.context, intent, null)
                }
            }
        }
    }
}