package com.kaumusic.kaum

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.kaumusic.kaum.databinding.ListAlbumLinearBinding


class MyLinearListAdapter (val albumlist: Array<Music>) : RecyclerView.Adapter<MyLinearListAdapter.Holder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
            val binding = ListAlbumLinearBinding.inflate(LayoutInflater.from(parent.context))
            return Holder(binding)
        }

        override fun getItemCount() = albumlist.size

        override fun onBindViewHolder(holder: Holder, position: Int) {
            holder.bind(albumlist[position])

        }

        class Holder(private val binding: ListAlbumLinearBinding) : RecyclerView.ViewHolder(binding.root) {
            fun bind(albumlist: Music) {
                binding.run {
                    imgLinearAlbum.setImageResource((R.drawable.img_album_exp4))
                    txtTitleAlbum.text = albumlist.title
                    txtSingerAlbum.text = albumlist.singer
                }
                binding.root.setOnClickListener {
                    it.findNavController()
                        .navigate(R.id.action_myPlayListFragment_to_myListFragment)
                }
            }
        }

}