package com.kaumusic.kaum

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidmusicapp.LatestChartAdapter
import com.kaumusic.kaum.databinding.FragmentChartBinding

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

    val popularlist = arrayOf(
        Chart("A" , "Oasis", "1"),
        Chart("Don't Look Back In Anger" , "Oasis", "2"),
        Chart("Don't Look Back In Anger" , "Oasis", "3"),
        Chart("Don't Look Back In Anger" , "Oasis", "4"),
        Chart("Don't Look Back In Anger" , "Oasis", "5"),
        Chart("Don't Look Back In Anger" , "Oasis", "6"),
        Chart("Don't Look Back In Anger" , "Oasis", "7"),
        Chart("Don't Look Back In Anger" , "Oasis", "8"),
        Chart("Don't Look Back In Anger" , "Oasis", "9"),
        Chart("Don't Look Back In Anger" , "Oasis", "10"),
        Chart("Don't Look Back In Anger" , "Oasis", "11"),
        Chart("Don't Look Back In Anger" , "Oasis", "12"),
        Chart("Don't Look Back In Anger" , "Oasis", "13"),
        Chart("Don't Look Back In Anger" , "Oasis", "14"),
        Chart("Don't Look Back In Anger" , "Oasis", "15"),
        Chart("Don't Look Back In Anger" , "Oasis", "16"),
        Chart("Don't Look Back In Anger" , "Oasis", "17"),
        Chart("Don't Look Back In Anger" , "Oasis", "18"),
        Chart("Don't Look Back In Anger" , "Oasis", "19"),
        Chart("Don't Look Back In Anger" , "Oasis", "20")
    )// sample List

    lateinit var binding : FragmentChartBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChartBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recLatest.run{
            adapter = LatestChartAdapter(latestChartlist)
            layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
        }

        binding.recPopular.run{
            adapter = PopularAdapter(popularlist)
            layoutManager = LinearLayoutManager(activity)
        }

    }

}