package com.kaumusic.kaum.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kaumusic.kaum.databinding.FragmentAlbumvideoBinding

class AlbumvideoFragment: Fragment() {
    lateinit var binding: FragmentAlbumvideoBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumvideoBinding.inflate(inflater,container,false)
        return binding.root
    }

}