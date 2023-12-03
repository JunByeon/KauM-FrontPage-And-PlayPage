package com.kaumusic.kaum

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidmusicapp.LatestChartAdapter
import com.kaumusic.kaum.databinding.FragmentChartBinding
import org.jsoup.Jsoup
import org.jsoup.select.Elements
import kotlin.concurrent.thread

class ChartFragment : Fragment() {
    val latestChartlist = arrayOf(
        Music("Don't Look Back In Anger" , "Oasis"),
        Music("Creep", "RadioHead"),
        Music("My Religion", "Troy Baker"),
        Music("Time is Running out", "Muse"),
        Music("Stand by me", "Oasis"),
        Music("No Surprises", "RadioHead"),
        Music("Don't Matter", "Kings of Leon"),
        Music("When I'm Gonna Lose You", "Local Natives"),
        Music("Crosses", "Jose Gonzalez"),
        Music("Mt.Washington", "Local Natives")
    )// sample List

    lateinit var binding : FragmentChartBinding
    val viewModel : musicViewModel by activityViewModels()

    override fun onStart() {
        super.onStart()
        viewModel.crawlData("https://www.melon.com/chart/index.htm")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChartBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.chart.observe(viewLifecycleOwner){
            binding.recPopular.adapter?.notifyDataSetChanged()
        }


        binding.recLatest.run{
            adapter = LatestChartAdapter(latestChartlist)
            layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
        }// 최신곡

        binding.recPopular.run{
            adapter = PopularAdapter(viewModel.chart)
            layoutManager = LinearLayoutManager(activity)
        }// 인기곡

    }

}