package com.kaumusic.kaum

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.select.Elements

class musicViewModel: ViewModel() {





    private val _chart = MutableLiveData<ArrayList<Chart>>()
    val chart : LiveData<ArrayList<Chart>> = _chart

    fun crawlData(url : String){
        viewModelScope.launch{
            withContext(Dispatchers.IO){
                val doc = Jsoup.connect(url).get()
                val element : Elements = doc.select("div#tb_list").select("tr.lst50")

                var rank : String
                var title : String
                var artist : String
                var album : String
                var coverImg : String
                val elemList = ArrayList<Chart>()

                for(elem in element){
                    elem.run{
                        rank = select("div.wrap.t_center").select("span.rank").text()
                        title = select("div.ellipsis.rank01").select("span").text()
                        artist = select("div.ellipsis.rank02").select("span").text()
                        album = select("div.ellipsis.rank03").select("a").text()
                        coverImg = select("div.wrap").select("a").select("img").attr("src")
                    }

                    elemList.add(Chart(rank, title, artist, album, coverImg))
                }
                _chart.postValue(elemList)
                }

        }
    }

}