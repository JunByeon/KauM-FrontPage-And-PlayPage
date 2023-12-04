package com.kaumusic.kaum

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kaumusic.kaum.databinding.FragmentMylistBinding
import com.kaumusic.kaum.viewmodel.MusicViewModel


class MyListFragment : Fragment() {
    val musiclist = arrayOf(
        Music("Don't Look Back In Anger" , "Oasis"),
        Music("Creep", "RadioHead"),
        Music("My Religion", "Troy Baker"),
        Music("Time is Running out", "Muse"),
        Music("Stand by me", "Oasis"),
        Music("No Surprises", "RadioHead"),
        Music("Don't Matter", "Kings of Leon"),
        Music("When I'm Gonna Lose You", "Local Natives"),
        Music("Crosses", "Jose Gonzalez"),
        Music("Mt.Washington", "Local Natives")
    )// sample List


    var binding: FragmentMylistBinding? = null
    val viewModel : MusicViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let{
            viewModel.getAlbum(it.getInt("Album id"))
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
            }
        }

        binding?.recMusic?.run{
            adapter = MyListAdapter(musiclist)
            layoutManager = LinearLayoutManager(activity)
        }

        binding?.btnListPlay?.setOnClickListener{
            Toast.makeText(
                this.context,
                "${musiclist[0].title}이 재생됩니다!",
                Toast.LENGTH_SHORT).show()
        }

        binding?.btnListModify?.setOnClickListener{
            /*
            val alert = AlertDialog.Builder(activity)
            alert.run{
                setTitle("앨범 이름 수정")
                setIcon(R.drawable.baseline_mode_edit_24)
                setMessage("앨범 이름을 수정한 후 확인을 누르세요.")
                setPositiveButton("확인", null)
                setNegativeButton("취소", null)
                show()
            }
             */


            val newName = EditText(activity)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}