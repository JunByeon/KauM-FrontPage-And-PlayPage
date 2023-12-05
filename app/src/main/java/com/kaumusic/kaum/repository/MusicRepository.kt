package com.kaumusic.kaum.repository

import android.app.Application
import com.kaumusic.kaum.Album
import com.kaumusic.kaum.AlbumDao
import com.kaumusic.kaum.SongDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.select.Elements


class MusicRepository(application: Application) {
    // <Connection
    // URL
    private val latestUrl = "https://www.melon.com/new/index.htm"
    private val popularUrl = "https://www.melon.com/chart/index.htm"

    // Room DB
    private val database = SongDatabase.getInstance(application)!!
    private val albumDao: AlbumDao = database.albumDao()

    // Connection/>

    // function
    suspend fun crawlLatest(): Elements? {
        return withContext(Dispatchers.IO) {
            val doc = Jsoup.connect(latestUrl).get()
            doc.select("div#songList").select("tbody").select("tr")
        }
    }

    suspend fun crawlChart(): Elements? {
        return withContext(Dispatchers.IO) {
            val doc = Jsoup.connect(popularUrl).get()
            doc.select("div#tb_list").select("tr.lst50")
        }
    }

    // <DB Call
    suspend fun getAlbum(id: Int): Album {
        return withContext(Dispatchers.IO) {
            albumDao.getAlbum(id)
        }
    }
    // DB Call/>
}