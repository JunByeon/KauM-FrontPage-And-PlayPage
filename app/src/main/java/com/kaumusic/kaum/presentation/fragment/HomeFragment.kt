package com.kaumusic.kaum.presentation.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.kaumusic.kaum.presentation.adapter.AlbumRVAdapter
import com.kaumusic.kaum.R
import com.kaumusic.kaum.data.db.SongDatabase
import com.kaumusic.kaum.databinding.FragmentHomeBinding
import com.kaumusic.kaum.domain.Album
import com.kaumusic.mynavapplication.viewmodel.AnswerViewModel
import java.util.ArrayList

class HomeFragment : Fragment() {
    // View Model의 life cycle이 activity에 물려서 종속이 되도록 아래와 같이 설정
    val viewModel: AnswerViewModel by activityViewModels()
    var binding: FragmentHomeBinding? = null
    private var albumDatas = ArrayList<Album>() // 앨범 리스트

    private lateinit var songDB: SongDatabase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)



        songDB = SongDatabase.getInstance(requireContext())!!
        Log.d("albumlist", albumDatas.toString())


        // 더미데이터랑 Adapter 연결
        val albumRVAdapter = AlbumRVAdapter()
        songDB.albumDao().getAlbums().forEach{
            albumDatas.add(it)
        }
        albumRVAdapter.setAlbums(albumDatas)


        // 리사이클러뷰에 어댑터를 연결
        binding!!. homeTodayMusicAlbumRv.adapter = albumRVAdapter

        albumRVAdapter.setMyItemClickListener(object : AlbumRVAdapter.MyItemClickListener {

            override fun onItemClick(album: Album) {
                navigateToMyListFragment(album)
            }

            override fun onRemoveAlbum(position: Int) {
                albumRVAdapter.removeItem(position)
            }
        })
        // 레이아웃 매니저 설정
        binding!!.homeTodayMusicAlbumRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 바뀐 것을 viewModel로부터 가져오기도 하고
        viewModel.answer.observe(viewLifecycleOwner) { // firebase에 저장이 되어있는 것 observe
            binding?.chkYN?.isChecked = viewModel.isYes
        }

        // 바뀐 것을 viewModel로 주기도 한다
        binding?.chkYN?.setOnClickListener {
            viewModel.setYes(binding?.chkYN?.isChecked ?: false)
        }

        binding?.btnResult?.setOnClickListener {
            // 어차피 ViewModel에 data 변화가 일어나기 때문에 굳이 bundle로 직접 data result를 넘겨줄 필요가 없음
            val destination = if (binding?.chkYN?.isChecked == true) R.id.action_homeFragment_to_resultFragment else R.id.action_homeFragment_self2
            findNavController().navigate(destination)
        }
    }


    private fun navigateToMyListFragment(album: Album) {
        val bundle = bundleOf("Album id" to album.id)
       findNavController().navigate(R.id.action_homeFragment_to_myListFragment, bundle) // bundle에 album id를 담아서 바로 mylistfragement로 jump 뛴다
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}