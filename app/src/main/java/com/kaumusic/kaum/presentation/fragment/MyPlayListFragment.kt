package com.kaumusic.kaum.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.kaumusic.kaum.presentation.adapter.MyLinearListAdapter
import com.kaumusic.kaum.presentation.adapter.PlayListAdapter
import com.kaumusic.kaum.R
import com.kaumusic.kaum.databinding.FragmentMyPlayListBinding
import com.kaumusic.kaum.domain.Music
import com.kaumusic.kaum.viewmodel.MusicViewModel

class MyPlayListFragment : Fragment() {

    var binding: FragmentMyPlayListBinding? = null
    private val viewModel : MusicViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyPlayListBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getAlbums()

        viewModel.albums.observe(viewLifecycleOwner){
            binding?.run{
                recAlbumlist.adapter?.notifyDataSetChanged()
            }
        }

        viewModel.isGridView.observe(viewLifecycleOwner){isGridView ->
            binding?.recAlbumlist?.run{
                if (isGridView){
                    adapter = PlayListAdapter(viewModel.albums)
                    layoutManager = GridLayoutManager(activity, 2)
                } else {
                    adapter = MyLinearListAdapter(viewModel.albums)
                    layoutManager = LinearLayoutManager(activity)
                } // Make View by Grid if(isGridView) else by Linear
            } // Make RecView of Album list
        } // observe isGridView value

        binding?.btnSortMethod?.setOnClickListener {
            //정렬 방식 변경 (미구현)
        }
        binding?.btnSortType?.setOnClickListener {
            viewModel.changeSortType()
            findNavController().navigate(R.id.action_myPlayListFragment_self)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}