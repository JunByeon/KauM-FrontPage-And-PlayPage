package com.kaumusic.kaum

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kaumusic.kaum.databinding.FragmentDetailBinding
import com.kaumusic.kaum.databinding.FragmentVideoBinding

class VideoFragment : Fragment() {
    lateinit var binding: FragmentVideoBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVideoBinding.inflate(inflater,container,false)

        return binding.root
    }
}