package com.kaumusic.kaum.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.kaumusic.kaum.domain.Song

class Converters {
    @TypeConverter
    fun listToJson(value : MutableList<Song>): String{
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToList(value : String): MutableList<Song>{
        return Gson().fromJson(value, Array<Song>::class.java).toMutableList()
    }
}