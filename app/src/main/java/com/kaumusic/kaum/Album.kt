package com.kaumusic.kaum

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.ArrayList

@Entity(tableName = "AlbumTable")
data class Album(
    @PrimaryKey(autoGenerate = false) var id: Int = 0, // album의 pk는 임의로 지정해주기 위해 autogenerate 안씀.
    var title: String? = "",
    var singer: String? = "",
    var coverImg: Int? = null
)
