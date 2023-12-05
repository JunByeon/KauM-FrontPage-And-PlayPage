package com.kaumusic.mynavapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kaumusic.kaum.data.repository.AnswerRepository

// defualt answer 설정
const val UNCHECKED_ANSWER = "yes"

class AnswerViewModel: ViewModel() {
    // 전형적인 패턴
    // view를 관찰하고 있는 외부에 있는 observer로 data가 바뀌면 바로 가져온다.
    // 안에서 보는 것이므로 바뀔 수 있도록(mutable) 함(private이므로 under score를 붙인다)
    private val _answer = MutableLiveData<String>(UNCHECKED_ANSWER)
    // 밖에서 보는 것은 바뀔 수 없도록 진행함
    val answer : LiveData<String> get() = _answer

    // observer 동작 실행
    private val repository = AnswerRepository()
    init {
        repository.observeAnswer(_answer)
    }

    // Firebase DB로부터 View로 Data Porting 진행
    private fun modifyAnswer(newValue:String) {
        val newAnswer = newValue.takeIf { it.isNotBlank() } ?: UNCHECKED_ANSWER
        repository.postAnswer(newAnswer)
    }

    // Check가 되었는지 알려준다
    val isYes: Boolean
        get() = _answer.value?.compareTo("yes") == 0

    // Yes로 answer를 setting 기능
    fun setYes(newValue: Boolean) {
        val answerValue = if (newValue) "yes" else "no"
        modifyAnswer(answerValue)
    }
}