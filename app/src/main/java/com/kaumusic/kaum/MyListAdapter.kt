package com.kaumusic.kaum

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.kaumusic.kaum.databinding.ListLinearBinding

class MyListAdapter(val musiclist: Array<Music>) : RecyclerView.Adapter<MyListAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ListLinearBinding.inflate(LayoutInflater.from(parent.context))
        return Holder(binding)
    }

    override fun getItemCount() = musiclist.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(musiclist[position])
    }

    class Holder(private val binding: ListLinearBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(musiclist : Music){
            binding.run{
                imageView.setImageResource((R.drawable.img_album_exp4))
                txtTitle.text = musiclist.title
                txtSinger.text = musiclist.singer
            }
            binding.root.setOnClickListener{
                Toast.makeText(
                    binding.root.context,
                    "${musiclist.title} - ${musiclist.singer} 가 재생됩니다!",
                    Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}