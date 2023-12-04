package com.kaumusic.kaum.viewmodel

import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.storage.FirebaseStorage
import com.kaumusic.kaum.entity.Video
import com.kaumusic.kaum.repository.VideoRepository
import com.kaumusic.mynavapplication.repository.AnswerRepository
import com.kaumusic.mynavapplication.viewmodel.UNCHECKED_ANSWER

class VideoViewModel : ViewModel() {
    private val videosLiveData = MutableLiveData<List<Video>>()

    fun getVideos() {
        FirebaseStorage.getInstance().reference.child("videos").listAll()
            .addOnSuccessListener { listResult ->
                val videoList = mutableListOf<Video>()

                listResult.items.forEach { storageReference ->
                    val video = Video()
                    video.title = storageReference.name

                    storageReference.downloadUrl.addOnCompleteListener(OnCompleteListener { task ->
                        val url = "https://${task.result?.encodedAuthority}${task.result?.encodedPath}?alt=media&token=${task.result?.getQueryParameters("token")?.get(0)}"
                        video.url = url
                        videoList.add(video)
                        videosLiveData.value = videoList
                    })
                }
            }
            .addOnFailureListener { e ->
                // Error handling
            }
    }

    fun getVideosLiveData(): LiveData<List<Video>> {
        return videosLiveData
    }



}