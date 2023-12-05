package com.kaumusic.kaum.repository

import androidx.lifecycle.MutableLiveData
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

// Realtime Firebase DB에서 받아올 Answer
class AnswerRepository {
    val database = Firebase.database
    val answerRef = database.getReference("answer")

    // Data Change가 Firebase단에서 이루어 졌을때 내 View에도 Data Change를 하기 위한 로직
    fun observeAnswer(answer: MutableLiveData<String>) {
        answerRef.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.value?.toString() ?: ""
                answer.postValue(value)
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    // Data Change가 내 View단에서 이루어 졌을때 Firebase DB 단에도 Data Change를 하기 위한 로직
    fun postAnswer(newValue : String) {
        answerRef.setValue(newValue)
    }
}