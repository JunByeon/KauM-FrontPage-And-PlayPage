package com.kaumusic.kaum.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kaumusic.kaum.domain.Album
import com.kaumusic.kaum.domain.Chart
import com.kaumusic.kaum.data.repository.MusicRepository
import com.kaumusic.kaum.domain.Song
import kotlinx.coroutines.launch

class MusicViewModel(application: Application) : AndroidViewModel(application) {
    //Repository
    private val repository = MusicRepository(application)

    // <LiveDatas
    private val _chart = MutableLiveData<ArrayList<Chart>>()
    val chart: LiveData<ArrayList<Chart>> = _chart

    private val _latest = MutableLiveData<ArrayList<Chart>>()
    val latest: LiveData<ArrayList<Chart>> = _latest

    private val _isGridView = MutableLiveData(true)
    val isGridView: LiveData<Boolean> = _isGridView

    private val _album = MutableLiveData<Album>()
    val album : LiveData<Album> = _album

    private val _albums = MutableLiveData<List<Album>>()
    val albums : LiveData<List<Album>> = _albums

    private val _songs = MutableLiveData<List<Song>>()
    val songs : LiveData<List<Song>> = _songs

    private val _songList = MutableLiveData<MutableList<Song>>()
    val songList : LiveData<MutableList<Song>> = _songList
    // LiveDatas/>


    // functions
    fun changeSortType() {
        _isGridView.value = _isGridView.value?.not() ?: true
    }

    fun getSongList(){
        _songList.value = _album.value?.songList ?: mutableListOf()
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

    // <DB Call
    fun getAlbum(id : Int){
        viewModelScope.launch {
            _album.value = repository.getAlbum(id)
        }
    }
    fun getAlbums(){
        viewModelScope.launch {
            _albums.value = repository.getAlbums()
        }
    }

    fun getSongs(){
        viewModelScope.launch {
            _songs.value = repository.getSongs()
        }
    }
    // DB Call/>


}