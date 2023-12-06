package com.kaumusic.kaum.presentation.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kaumusic.kaum.presentation.adapter.MyListAdapter
import com.kaumusic.kaum.R
import com.kaumusic.kaum.databinding.FragmentMylistBinding
import com.kaumusic.kaum.presentation.activity.SongActivity
import com.kaumusic.kaum.viewmodel.MusicViewModel


class MyListFragment : Fragment() {

    var binding: FragmentMylistBinding? = null
    private val viewModel : MusicViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let{bundle ->
            viewModel.getAlbum(bundle.getInt("Album id"))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?)
    : View? {
        binding =  FragmentMylistBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.album.observe(viewLifecycleOwner){album ->
            binding?.run{
                txtAlbumTitle.text = album.title
                imgAlbum.setImageResource(album.coverImg ?: R.drawable.baseline_album_24)
            }
            viewModel.getSongList()
        }

        viewModel.songList.observe(viewLifecycleOwner){
            binding?.run{
                recMusic.adapter?.notifyDataSetChanged()
            }
        }

        binding?.recMusic?.run{
            adapter = MyListAdapter(viewModel.songList)
            layoutManager = LinearLayoutManager(activity)
        }

        binding?.btnListPlay?.setOnClickListener{
            val intent = Intent(activity, SongActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}