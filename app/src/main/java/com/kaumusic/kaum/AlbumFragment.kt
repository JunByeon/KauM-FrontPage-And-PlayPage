package com.kaumusic.kaum

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.kaumusic.kaum.databinding.FragmentAlbumBinding

class AlbumFragment : Fragment() {
    private lateinit var binding: FragmentAlbumBinding

    private val information = arrayListOf("수록곡", "상세정보", "영상")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumBinding.inflate(inflater, container, false)

        binding.albumBackIv.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm , HomeFragment())
                .commitAllowingStateLoss()
        }

        val albumAdpter = AlbumVPAdapter(this)
        binding.albumContentVp.adapter = albumAdpter
        TabLayoutMediator(binding.albumContentTb, binding.albumContentVp) {
            tab, position ->
            tab.text = information[position]
        }.attach()

        return binding.root
    }

}