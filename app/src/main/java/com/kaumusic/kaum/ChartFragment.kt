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

    lateinit var binding : FragmentChartBinding
    val viewModel : musicViewModel by activityViewModels()

//    override fun onStart() {
//        super.onStart()
//        // 멜론 차트 최신곡 Top 50 Crawl
//        viewModel.crawlLatest("https://www.melon.com/new/index.htm")
//        // 멜론 차트 인기곡 Top 50 Crawl
//        viewModel.crawlChart("https://www.melon.com/chart/index.htm")
//    }

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
            binding.run{
                recPopular.adapter?.notifyDataSetChanged()
                recLatest.adapter?.notifyDataSetChanged()
            }// notify DataSet Change to adapters exist.
        }// Observe ViewModel.

        binding.recLatest.run{
            adapter = LatestChartAdapter(viewModel.latest)
            layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
        }// show RecView: chart(Latest) as Horizontal View


        binding.recPopular.run{
            adapter = PopularAdapter(viewModel.chart)
            layoutManager = LinearLayoutManager(activity)
        }// show RecView: chart(Popular) as Vertical View

    }

}