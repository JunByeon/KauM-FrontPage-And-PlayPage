package com.kaumusic.kaum.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.kaumusic.kaum.R
import com.kaumusic.kaum.view.adapter.BannerVPAdapter
import com.kaumusic.kaum.databinding.FragmentHomeBinding
import com.kaumusic.kaum.model.entity.Album
import com.kaumusic.kaum.view.adapter.AlbumRVAdapter
import java.util.ArrayList

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    private var albumDatas = ArrayList<Album>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

//        binding.homeAlbumImgIv1.setOnClickListener {
//            (context as MainActivity).supportFragmentManager.beginTransaction()
//                .replace(R.id.main_frm , AlbumFragment())
//                .commitAllowingStateLoss()
//        }

        albumDatas.apply {
            add(Album("Butter", " 방탄소년단 (BTS)", R.drawable.img_album_exp))
            add(Album("Lilac", " 아이유 (IU)", R.drawable.img_album_exp2))
            add(Album("Next Level", " 에스파", R.drawable.img_album_exp3))
            add(Album("Boy with luv", " 방탄소년단 (BTS)", R.drawable.img_album_exp4))
            add(Album("BBoom BBom", " 모모랜드", R.drawable.img_album_exp5))
            add(Album("Weekend", " 태연", R.drawable.img_album_exp6))

        }

        val albumRvAdapter = AlbumRVAdapter(albumDatas)
        binding.homeTodayMusicAlbumRv.adapter = albumRvAdapter
        binding.homeTodayMusicAlbumRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        albumRvAdapter.setMyItemClickListener(object: AlbumRVAdapter.MyItemClickListener) {

        }

        val bannerAdapter = BannerVPAdapter(this)
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp2))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp2))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp2))

        binding.homeBannerVp.adapter = bannerAdapter
        binding.homeBannerVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        return binding.root
    }
}