package com.kaumusic.kaum.data.db


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kaumusic.kaum.data.dao.AlbumDao
import com.kaumusic.kaum.data.dao.SongDao
import com.kaumusic.kaum.domain.Album
import com.kaumusic.kaum.domain.Song


@Database(entities = [Song::class, Album::class], version = 7)
abstract class SongDatabase : RoomDatabase() {

    abstract fun albumDao(): AlbumDao
    abstract fun songDao(): SongDao
//    abstract fun userDao(): UserDao


    companion object {
        private var instance: SongDatabase? = null

        @Synchronized
        fun getInstance(context: Context): SongDatabase? {
            if (instance == null) {
                synchronized(SongDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SongDatabase::class.java,
                        "song-database"//다른 데이터 베이스랑 이름겹치면 꼬임
                    ).fallbackToDestructiveMigration().allowMainThreadQueries()
                        .build() // 메인 스레드에서 쿼리를 허용
                }
            }

            return instance
        }
    }
}