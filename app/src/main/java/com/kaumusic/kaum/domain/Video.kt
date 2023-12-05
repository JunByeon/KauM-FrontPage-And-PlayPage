package com.kaumusic.kaum.domain

// Firebase Storage에서 받아올 Data의 data class
data class Video(
    var title: String = "Title",
    var url: String? = null
)
