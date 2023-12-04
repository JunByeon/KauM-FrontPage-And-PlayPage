package com.kaumusic.kaum.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kaumusic.kaum.databinding.VideoListItemBinding
import com.kaumusic.kaum.entity.Video

// Video을 넣을 Recycler View를 다룰 Adapter
class VideoRVAdapter(
    private val context: Context,   // requireContext()로 parent의 context를 받는다
    private val arrayList: ArrayList<Video>
) : RecyclerView.Adapter<VideoRVAdapter.ViewHolder>() {

    var itemClickListner: OnItemClickListener? = null

    // Viewholder를 parent에 꽂아둔다.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            VideoListItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    // viewholder가 진행할 logic으로 위치별로 video 주입
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val video = arrayList[position]
        holder.binding.listItemTitle.text = video.title

        // Glide.with()에 parent의 context의 applicationContext 사용하여 url에서 온 image 꽂아둠
        Glide.with(context.applicationContext).load(video.url).into(holder.binding.listItemImage)

        // view holder가 눌리면 video 재생
        holder.binding.root.setOnClickListener {
            itemClickListner?.onClick(video)
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    inner class ViewHolder(val binding: VideoListItemBinding) : RecyclerView.ViewHolder(binding.root)

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListner = onItemClickListener
    }

    interface OnItemClickListener {
        fun onClick(video: Video)
    }
}
