package com.kaumusic.kaum.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.kaumusic.kaum.R
import com.kaumusic.kaum.databinding.ListGridBinding
import com.kaumusic.kaum.domain.Album

class PlayListAdapter(val albumlist: LiveData<List<Album>>) :
    RecyclerView.Adapter<PlayListAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ListGridBinding.inflate(LayoutInflater.from(parent.context))
        return Holder(binding)
    }

    override fun getItemCount() = albumlist.value?.size ?: 0

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(albumlist.value?.getOrNull(position))
    }

    class Holder(private val binding: ListGridBinding) : ViewHolder(binding.root) {
        fun bind(albumlist: Album?) {
            albumlist?.let{albumlist ->
                binding.run {
                    imgAlbumAlbumlist.setImageResource(albumlist.coverImg ?: R.drawable.baseline_album_24)
                    txtAlbumtitle.text = albumlist.title
                    txtAlbuminfo.text = albumlist.singer
                }

                binding.root.setOnClickListener {
                    val bundle = bundleOf("Album id" to albumlist.id)
                    // 1은 임의값, 실제 Album의 id를 넘겨주어야 함.
                    it.findNavController().navigate(R.id.action_myPlayListFragment_to_myListFragment, bundle)
                }
            }
        }
    }
}