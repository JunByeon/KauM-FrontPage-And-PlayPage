package com.kaumusic.kaum

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.flo.SongActivity
//import com.example.flo.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.kaumusic.kaum.ScopeProvider.databaseScope
//import com.kaumusic.kaum.SongActivity
import com.kaumusic.kaum.databinding.ActivityMainBinding
import com.kaumusic.kaum.viewmodel.MusicViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    // 상단 Action Bar는 계속 오류가 나는 관계로 사용하지 않도록 결정함
    // binding을 밖에 빼서 선언하는 이유는 이 binding을 전역적으로 선언하여 다른 곳에서도 사용하기 위해서이다.
    // private으로 선언하지 않은 이유 : MainActivity부터 lifecycle이 시작이 되는데 굳이 private으로 해서 막아줄 필요가 있을까?


    lateinit var binding: ActivityMainBinding
    private lateinit var viewModel : MusicViewModel

    //    private var song: Song = Song()
    private var gson: Gson = Gson()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // <lateinit
        viewModel = ViewModelProvider(this)[MusicViewModel::class.java]
        binding = ActivityMainBinding.inflate(layoutInflater)
        // lateinit/>

        // <values
        val navController = binding.mainNavView.getFragment<NavHostFragment>().navController
        // values/>

        viewModel.run{
            // 멜론 차트 최신곡 Top 50 Crawl
            getLatest()
            // 멜론 차트 인기곡 Top 50 Crawl
            getChart()
        } // initialize Chart Data on viewModel, while onCreate

        binding.mainBnv.setupWithNavController(navController)

        setTheme(R.style.Theme_FLO)
        setContentView(binding.root)

        inputDummySongs()
        inputDummyAlbums()



        binding.mainPlayerCl.setOnClickListener {
            val editor = getSharedPreferences("song", MODE_PRIVATE).edit()
//            editor.putInt("songId", song.id)
            editor.apply()

            val intent = Intent(this, SongActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        val spf = getSharedPreferences("song", MODE_PRIVATE)
        val songId = spf.getInt("songId", 0)

        val songDB = SongDatabase.getInstance(this)!!


        // 스레드를 지정하지 않으면 디폴트로 메인 스레드임
        // lifecycleScope: 엑티비티 수명주기에 반응하는 scope
        // DB 저장하는 부분만 io 쓰레드로 변경

        lifecycleScope.launch {
            val song = if (songId == 0) {
                databaseScope.async { songDB.songDao().getSong(1) }.await()
            } else {
                databaseScope.async { songDB.songDao().getSong(songId) }.await()
            }
            setMiniPlayer(song)
        }

    }


    private fun getJwt(): String? {
        val spf = this.getSharedPreferences("auth2", AppCompatActivity.MODE_PRIVATE)

        return spf!!.getString("jwt", "")
    }


//    private fun setMiniPlayer(song: Song) {
//        binding.mainMiniplayerTitleTv.text = song.title
//        binding.mainMiniplayerSingerTv.text = song.singer
//        binding.mainMiniplayerProgressSb.progress = (song.second * 100000) / song.playTime
//    }
//
//    private fun showFragment(fragmentContainer: Int, fragment: Fragment) {
//        supportFragmentManager.beginTransaction().apply {
//            replace(fragmentContainer, fragment)
//            commit()
//        }
//    }


    private fun setMiniPlayer(song: Song) {
        binding.mainMiniplayerTitleTv.text = song.title
        binding.mainMiniplayerSingerTv.text = song.singer
        binding.mainMiniplayerProgressSb.progress = (song.second * 100000) / song.playTime
    }

    private fun inputDummySongs() {
        val songDB = SongDatabase.getInstance(this)!!
        val songs = songDB.songDao().getSongs()

        if (songs.isNotEmpty()) return

        songDB.songDao().insert(
            Song(
                "Lilac",
                "아이유 (IU)",
                0,
                200,
                false,
                "music_lilac",
                R.drawable.img_album_exp2,
                false,
            )
        )

        songDB.songDao().insert(
            Song(
                "Flu",
                "아이유 (IU)",
                0,
                200,
                false,
                "music_flu",
                R.drawable.img_album_exp2,
                false,
            )
        )

        songDB.songDao().insert(
            Song(
                "Butter",
                "방탄소년단 (BTS)",
                0,
                190,
                false,
                "music_butter",
                R.drawable.img_album_exp,
                false,
            )
        )

        songDB.songDao().insert(
            Song(
                "Next Level",
                "에스파 (AESPA)",
                0,
                210,
                false,
                "music_next",
                R.drawable.img_album_exp3,
                false,
            )
        )


        songDB.songDao().insert(
            Song(
                "Boy with Luv",
                "music_boy",
                0,
                230,
                false,
                "music_lilac",
                R.drawable.img_album_exp4,
                false,
            )
        )


        songDB.songDao().insert(
            Song(
                "BBoom BBoom",
                "모모랜드 (MOMOLAND)",
                0,
                240,
                false,
                "music_bboom",
                R.drawable.img_album_exp5,
                false,
            )
        )

        val _songs = songDB.songDao().getSongs()
        Log.d("DB data", _songs.toString())
    }

    //ROOM_DB
    private fun inputDummyAlbums() {
        val songDB = SongDatabase.getInstance(this)!!
        val albums = songDB.albumDao().getAlbums()

        if (albums.isNotEmpty()) return

        songDB.albumDao().insert(
            Album(
                0,
                "IU 5th Album 'LILAC'", "아이유 (IU)", R.drawable.img_album_exp2
            )
        )

        songDB.albumDao().insert(
            Album(
                1,
                "Butter", "방탄소년단 (BTS)", R.drawable.img_album_exp
            )
        )

        songDB.albumDao().insert(
            Album(
                2,
                "iScreaM Vol.10 : Next Level Remixes", "에스파 (AESPA)", R.drawable.img_album_exp3
            )
        )

        songDB.albumDao().insert(
            Album(
                3,
                "MAP OF THE SOUL : PERSONA", "방탄소년단 (BTS)", R.drawable.img_album_exp4
            )
        )

        songDB.albumDao().insert(
            Album(
                4,
                "GREAT!", "모모랜드 (MOMOLAND)", R.drawable.img_album_exp5
            )
        )

    }
}


