package com.kaumusic.kaum.view.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.kaumusic.kaum.view.fragment.DetailFragment
import com.kaumusic.kaum.view.fragment.SongFragment
import com.kaumusic.kaum.view.fragment.VideoFragment

class AlbumVPAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0-> SongFragment()
            1-> DetailFragment()
            else-> VideoFragment()
        }
    }

}