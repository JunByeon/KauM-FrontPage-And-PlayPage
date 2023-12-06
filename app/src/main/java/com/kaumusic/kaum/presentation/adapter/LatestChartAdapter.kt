package com.kaumusic.kaum.presentation.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kaumusic.kaum.R
import com.kaumusic.kaum.databinding.ListHorizontalBinding
import com.kaumusic.kaum.domain.Chart

class LatestChartAdapter(private val latestChartlist: LiveData<ArrayList<Chart>>) :
    RecyclerView.Adapter<LatestChartAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ListHorizontalBinding.inflate(LayoutInflater.from(parent.context))
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(latestChartlist.value?.getOrNull(position))
    }

    override fun getItemCount() = latestChartlist.value?.size ?: 0

    class Holder(private val binding: ListHorizontalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(latest: Chart?) {
            latest?.let {elem ->
                binding.run {
                    //Initialize coverImg by url else default
                    Glide.with(root)
                        .load(elem.coverImg)
                        .error(R.drawable.baseline_album_24)
                        .into(imgMusic)
                    txtTitle.text = elem.album
                    txtSinger.text = elem.artist
                }
            }

            binding.root.setOnClickListener {
                it.context.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://www.melon.com/new/index.htm")
                    )
                )
            }
        }
    }
}