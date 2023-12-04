package com.kaumusic.kaum.view.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.storage.FirebaseStorage
import com.kaumusic.kaum.R
import com.kaumusic.kaum.databinding.FragmentResultBinding
import com.kaumusic.mynavapplication.viewmodel.AnswerViewModel
import com.kaumusic.kaum.entity.Video
import com.kaumusic.kaum.view.adapter.VideoRVAdapter
import com.kaumusic.kaum.viewmodel.VideoViewModel
import io.reactivex.Observer

class ResultFragment : Fragment() {
    // View Model의 life cycle이 activity에 물려서 종속이 되도록 아래와 같이 설정
    val answerViewModel: AnswerViewModel by activityViewModels()
    val videoViewModel : VideoViewModel by activityViewModels()
    var binding : FragmentResultBinding? = null
    var adapter: VideoRVAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentResultBinding.inflate(inflater)
        // View가 생성이 다 된 후에 Firebase Storage에서 동영상을 불러와서 해당 View에 띄우는 과정은 Overloading이 너무 높으므로 아래 onViewCreated로 빼지 않고 이곳에 둔다.
        // MVVM으로 기능을 분리할까 하다가 그냥 이미 static한 Firebase Storage에서 동영상을 받아오기만 하므로 View단에서 logic을 처리하도록 함
        // binding에 null이 들어올 수도 있으므로 그냥 아싸리 위에다가 전역적으로 binding을 선언해줄까 하다가 그냥 Code Convention을 맞추기 위해 아래와 같이 처리함
        val recyclerView: RecyclerView = binding!!.recycler

        adapter = VideoRVAdapter(requireContext(), mutableListOf())
        adapter.setOnItemClickListener(object : VideoRVAdapter.OnItemClickListener {
            override fun onClick(video: Video) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(video.url))
                intent.setDataAndType(Uri.parse(video.url), "video/mp4")
                startActivity(intent)
            }
        })
        recyclerView.adapter = adapter
        // Observe the LiveData and update the UI when the data changes
        videoViewModel.getVideosLiveData().observe(viewLifecycleOwner, Observer { videos ->
            videos?.let {
                adapter.setVideoList(it)
            }
        })

        // Trigger the data loading
        videoViewModel.getVideos()

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // live data의 변경을 가져다가 data binding한다.
        answerViewModel.answer.observe(viewLifecycleOwner) {
            // observe한다. 바뀌거나 처음보는 경우엔 읽는다.
            binding?.txtResult?.text = answerViewModel.answer.value
        }
        binding?.btnReexamine?.setOnClickListener {
            findNavController().navigate(R.id.action_resultFragment_to_homeFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}