package com.kaumusic.kaum.presentation.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kaumusic.kaum.R
import com.kaumusic.kaum.databinding.ListLinearRankedBinding
import com.kaumusic.kaum.domain.Chart

class PopularAdapter(private val popularlist: LiveData<ArrayList<Chart>>) :
    RecyclerView.Adapter<PopularAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ListLinearRankedBinding.inflate(LayoutInflater.from(parent.context))
        return Holder(binding)
    }

    override fun getItemCount() = popularlist.value?.size ?: 0

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(popularlist.value?.getOrNull(position))
    }
    class Holder(private val binding: ListLinearRankedBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(popular: Chart?) {
            popular?.let {elem ->
                binding.run {
                    //Initialize coverImg by url else default
                    Glide.with(root)
                        .load(elem.coverImg)
                        .error(R.drawable.baseline_album_24)
                        .into(imgElbum)
                    //text init
                    txtTitlePopular.text = elem.title
                    txtSingerPopular.text = elem.artist
                    txtRate.text = elem.rank
                }
            }
            binding.root.setOnClickListener {
                it.context.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://www.melon.com/chart/index.htm")
                    )
                )
            }
        }
    }
}
