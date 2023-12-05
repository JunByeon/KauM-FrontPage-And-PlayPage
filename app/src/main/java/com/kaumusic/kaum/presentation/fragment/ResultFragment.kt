package com.kaumusic.kaum.presentation.fragment

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
import com.kaumusic.kaum.domain.Video
import com.kaumusic.kaum.presentation.adapter.VideoRVAdapter

class ResultFragment : Fragment() {
    // View Model의 life cycle이 activity에 물려서 종속이 되도록 아래와 같이 설정
    val viewModel: AnswerViewModel by activityViewModels()

    var binding : FragmentResultBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentResultBinding.inflate(inflater)
        // View가 생성이 다 된 후에 Firebase Storage에서 동영상을 불러와서 해당 View에 띄우는 과정은 Overloading이 너무 높으므로 아래 onViewCreated로 빼지 않고 이곳에 둔다.
        // MVVM으로 기능을 분리할까 하다가 그냥 이미 static한 Firebase Storage에서 동영상을 받아오기만 하므로 View단에서 logic을 처리하도록 함
        // binding에 null이 들어올 수도 있으므로 그냥 아싸리 위에다가 전역적으로 binding을 선언해줄까 하다가 그냥 Code Convention을 맞추기 위해 아래와 같이 처리함
        val recyclerView: RecyclerView = binding!!.recycler

        // Firebase Storage에서 동영상 가져오기 기능
        FirebaseStorage.getInstance().reference.child("videos").listAll()
            .addOnSuccessListener { listResult -> // 일단 Firebase Storage에 있는 videos라는 폴더의 모든 동여상을 담아서 list로 만든다
                val arrayList = ArrayList<Video>()
                val adapter = VideoRVAdapter(requireContext(), arrayList) // adapter가 Recycler View에 Data 꽂아넣음
                adapter.setOnItemClickListener(object : VideoRVAdapter.OnItemClickListener {
                    override fun onClick(video: Video) { // video가 클릭이 되면 아래와 같이 새로운 activi
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(video.url))
                        intent.setDataAndType(Uri.parse(video.url), "video/mp4")
                        startActivity(intent)
                    }
                })
                recyclerView.adapter = adapter
                listResult.items.forEach { storageReference ->
                    val video = Video() // 각각의 video에 대해서 url을 mapping 시켜주고 title 등을 받아옴
                    video.title = storageReference.name
                    storageReference.downloadUrl.addOnCompleteListener(OnCompleteListener { task ->
                        val url =
                            "https://${task.result?.encodedAuthority}${task.result?.encodedPath}?alt=media&token=${task.result?.getQueryParameters("token")?.get(0)}"
                        video.url = url
                        arrayList.add(video)
                        adapter.notifyDataSetChanged()
                    })
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(requireContext(), "Failed to retrieve videos", Toast.LENGTH_SHORT)
                    .show()
            }

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // live data의 변경을 가져다가 data binding한다.
        viewModel.answer.observe(viewLifecycleOwner) {
            // observe한다. 바뀌거나 처음보는 경우엔 읽는다.
            binding?.txtResult?.text = viewModel.answer.value
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