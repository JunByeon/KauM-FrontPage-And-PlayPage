package com.kaumusic.kaum.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kaumusic.kaum.presentation.adapter.LatestChartAdapter
import com.kaumusic.kaum.presentation.adapter.PopularAdapter
import com.kaumusic.kaum.databinding.FragmentChartBinding
import com.kaumusic.kaum.viewmodel.MusicViewModel

class ChartFragment : Fragment() {

    var binding : FragmentChartBinding? = null
    private val viewModel : MusicViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChartBinding.inflate(inflater, container, false)
        return binding?.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.chart.observe(viewLifecycleOwner){
            binding?.run{
                recPopular.adapter?.notifyDataSetChanged()
                recLatest.adapter?.notifyDataSetChanged()
            }// notify DataSet Change to adapters exist.
        }// Observe ViewModel.

        binding?.recLatest?.run{
            adapter = LatestChartAdapter(viewModel.latest)
            layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
        }// show RecView: chart(Latest) as Horizontal View

        binding?.recPopular?.run{
            adapter = PopularAdapter(viewModel.chart)
            layoutManager = LinearLayoutManager(activity)
        }// show RecView: chart(Popular) as Vertical View

    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}