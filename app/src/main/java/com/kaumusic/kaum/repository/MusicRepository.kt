package com.kaumusic.kaum.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.select.Elements

class MusicRepository {
    private val latestUrl = "https://www.melon.com/new/index.htm"
    private val popularUrl = "https://www.melon.com/chart/index.htm"

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
}