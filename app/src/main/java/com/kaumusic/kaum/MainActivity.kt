package com.kaumusic.kaum

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.flo.SongActivity
//import com.example.flo.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.kaumusic.kaum.ScopeProvider.databaseScope
//import com.kaumusic.kaum.SongActivity
import com.kaumusic.kaum.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    private var song: Song = Song()
    private var gson: Gson = Gson()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_FLO)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        inputDummySongs()
//      inputDummyAlbums()
        val navController
                = binding.mainNavView.getFragment<NavHostFragment>().navController
        binding.mainBnv.setupWithNavController(navController)

        binding.mainPlayerCl.setOnClickListener {
            val editor = getSharedPreferences("song", MODE_PRIVATE).edit()
            editor.putInt("songId", song.id)
            editor.apply()

            val intent = Intent(this, SongActivity::class.java)
            startActivity(intent)
        }

        Log.d("MAIN/JWT_TO_SERVER", getJwt().toString())

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
            song = if (songId == 0) {
                databaseScope.async { songDB.songDao().getSong(1) }.await()
            } else {
                databaseScope.async { songDB.songDao().getSong(songId) }.await()
            }

            Log.d("song ID", song.id.toString())
            setMiniPlayer(song)

        }

    }


    private fun getJwt(): String? {
        val spf = this.getSharedPreferences("auth2", AppCompatActivity.MODE_PRIVATE)

        return spf!!.getString("jwt", "")
    }


    private fun setMiniPlayer(song: Song) {
        binding.mainMiniplayerTitleTv.text = song.title
        binding.mainMiniplayerSingerTv.text = song.singer
        binding.mainMiniplayerProgressSb.progress = (song.second * 100000) / song.playTime
    }

    private fun inputDummySongs(){
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



}