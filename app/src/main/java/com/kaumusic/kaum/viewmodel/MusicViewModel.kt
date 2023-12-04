package com.kaumusic.kaum.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaumusic.kaum.Chart
import com.kaumusic.kaum.repository.MusicRepository
import kotlinx.coroutines.launch

class MusicViewModel : ViewModel() {

    private val repository = MusicRepository()

    private val _chart = MutableLiveData<ArrayList<Chart>>()
    val chart: LiveData<ArrayList<Chart>> = _chart

    private val _latest = MutableLiveData<ArrayList<Chart>>()
    val latest: LiveData<ArrayList<Chart>> = _latest

    private val _isGridView = MutableLiveData(true)
    val isGridView: LiveData<Boolean> = _isGridView

    fun changeSortType() {
        _isGridView.value = _isGridView.value?.not() ?: true
    }

    fun getLatest(){
        viewModelScope.launch {
            repository.crawlLatest()?.let { element ->
                val elemList = ArrayList<Chart>()
                for (elem in element) {
                    elem.run {
                        elemList.add(
                            Chart(
                                rank = select("div.wrap.t_center").select("span.rank").text(),
                                title = select("div.ellipsis.rank01").select("span").text(),
                                artist = select("div.ellipsis.rank02").select("span").text(),
                                album = select("div.ellipsis.rank03").select("a").text(),
                                coverImg = select("div.wrap").select("a").select("img").attr("src")
                            )
                        )
                    }
                }
                _latest.value = elemList
            }
        }
    }

    fun getChart() {
        viewModelScope.launch {
            repository.crawlChart()?.let { element ->
                val elemList = ArrayList<Chart>()
                for (elem in element) {
                    elem.run {
                        elemList.add(
                            Chart(
                                rank = select("div.wrap.t_center").select("span.rank").text(),
                                title = select("div.ellipsis.rank01").select("span").text(),
                                artist = select("div.ellipsis.rank02").select("span").text(),
                                album = select("div.ellipsis.rank03").select("a").text(),
                                coverImg = select("div.wrap").select("a").select("img").attr("src")
                            )
                        )
                    }
                }
                _chart.value = elemList
            }
        }
    }
}