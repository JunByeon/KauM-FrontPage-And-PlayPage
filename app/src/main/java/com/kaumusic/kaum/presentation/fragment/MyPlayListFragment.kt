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

    val albumlist = arrayOf(
        Music("Album_1", "Oasis"),
        Music("Album_2", "Oasis"),
        Music("Album_3", "Oasis"),
        Music("Album_4", "Oasis"),
        Music("Album_5", "Oasis"),
        Music("Album_6", "Oasis"),
        Music("Album_7", "Oasis"),
        Music("Album_8", "Oasis"),
        Music("Album_9", "Oasis"),
        Music("Album_10", "Oasis"),
        Music("Album_11", "Oasis"),
        Music("Album_12", "Oasis"),
        Music("Album_13", "Oasis"),
        Music("Album_14", "Oasis"),
        Music("Album_15", "Oasis"),
        Music("Album_16", "Oasis"),
        Music("Album_17", "Oasis"),
        Music("Album_18", "Oasis"),
        Music("Album_19", "Oasis"),
        Music("Album_20", "Oasis"),
        Music("Album_21", "Oasis"),
        Music("Album_22", "Oasis"),
        Music("Album_23", "Oasis"),
        Music("Album_24", "Oasis"),
        Music("Album_25", "Oasis"),
        Music("Album_26", "Oasis"),
        Music("Album_27", "Oasis"),
        Music("Album_28", "Oasis"),
        Music("Album_29", "Oasis"),
        Music("Album_30", "Oasis"),
        Music("Album_31", "Oasis"),
        Music("Album_32", "Oasis"),
        Music("Album_33", "Oasis"),
        Music("Album_34", "Oasis"),
        Music("Album_35", "Oasis"),
        Music("Album_36", "Oasis"),
        Music("Album_37", "Oasis"),
        Music("Album_38", "Oasis"),
        Music("Album_39", "Oasis"),
        Music("Album_40", "Oasis"),
        Music("Album_41", "Oasis"),
        Music("Album_42", "Oasis"),
        Music("Album_43", "Oasis"),
        Music("Album_44", "Oasis"),
        Music("Album_45", "Oasis"),
        Music("Album_46", "Oasis"),
        Music("Album_47", "Oasis"),
        Music("Album_48", "Oasis"),
        Music("Album_49", "Oasis"),
        Music("Album_50", "Oasis"),
    )// sample List

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