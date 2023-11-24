package com.kaumusic.kaum

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.kaumusic.kaum.R
import com.kaumusic.kaum.databinding.ListLinearRankedBinding

class PopularAdapter (val popularlist: Array<Chart>) : RecyclerView.Adapter<PopularAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ListLinearRankedBinding.inflate(LayoutInflater.from(parent.context))
        return Holder(binding)
    }

    override fun getItemCount() = (popularlist.size)

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(popularlist[position])
    }

    class Holder(private val binding: ListLinearRankedBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(popularlist : Chart){
            binding.run{
                imgElbum.setImageResource((R.drawable.img_album_exp4))
                txtTitlePopular.text = popularlist.title
                txtSingerPopular.text = popularlist.singer
                txtRate.text = popularlist.rate
            }

            binding.root.setOnClickListener{
                Toast.makeText(
                    binding.root.context,
                    "${popularlist.title} - ${popularlist.singer}, ${popularlist.rate}위",
                    Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}