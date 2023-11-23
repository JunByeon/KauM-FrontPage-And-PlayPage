package com.kaumusic.kaum


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Song::class], version = 1)
abstract class SongDatabase: RoomDatabase() {

    abstract fun albumDao(): AlbumDao

    abstract fun songDao(): SongDao
//    abstract fun userDao(): UserDao


    companion object {
        private var instance: SongDatabase? = null

        @Synchronized
        fun getInstance(context: Context): SongDatabase? {
            if (instance == null) {
                synchronized(SongDatabase::class){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SongDatabase::class.java,
                        "song-database"//다른 데이터 베이스랑 이름겹치면 꼬임
                    ).allowMainThreadQueries().build() // 메인 스레드에서 쿼리를 허용
                }
            }

            return instance
        }
    }
}