package com.del.testandroiddeveloper.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.del.testandroiddeveloper.data.Resource
import com.del.testandroiddeveloper.databinding.FragmentHomeBinding
import com.del.testandroiddeveloper.ui.adapter.MovieAdapter
import org.koin.android.viewmodel.ext.android.viewModel
import com.smarteist.autoimageslider.SliderView

import com.del.testandroiddeveloper.ui.adapter.SliderAdapter
import com.del.testandroiddeveloper.ui.detail.DetailActivity


class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModel()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var movieCoomingSoonAdapter: MovieAdapter
    private lateinit var slideAdapter: SliderAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        movieAdapter = MovieAdapter()
        activity?.actionBar?.hide()
        movieCoomingSoonAdapter = MovieAdapter()
        slideAdapter = SliderAdapter()
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding.rvPopular){
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            this.adapter = movieAdapter
        }

        with(binding.rvComingSoon){
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            this.adapter = movieCoomingSoonAdapter
        }
        observeDataMovie()

        binding.sliderViewImage.apply {
            autoCycleDirection = SliderView.LAYOUT_DIRECTION_LTR

            setSliderAdapter(slideAdapter)
            scrollTimeInSec = 3
            isAutoCycle = true
            startAutoCycle()
        }
        observeSliderMovie()

        movieAdapter.onItemClick ={ selectedData ->
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_MOVIE, selectedData)
            startActivity(intent)
        }

        movieCoomingSoonAdapter.onItemClick={ selectedData ->
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_MOVIE, selectedData)
            startActivity(intent)
        }

        slideAdapter.onItemClick={ selectedData ->
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_MOVIE, selectedData)
            startActivity(intent)
        }

    }

    private fun observeDataMovie(){
        homeViewModel.getPopularMovie().observe(viewLifecycleOwner, {movies ->
            if (movies!=null){
                when(movies){
                    is Resource.Loading-> println("Loading")
                    is Resource.Success->{
                        movieAdapter.setData(movies.data)
                    }
                    is Resource.Error-> println("Error")
                }
            }
        })

        homeViewModel.getComingSoonMovie().observe(viewLifecycleOwner, {movies ->
            if (movies!=null){
                when(movies){
                    is Resource.Loading-> println("Loading")
                    is Resource.Success->{
                        movieCoomingSoonAdapter.setData(movies.data)
                    }
                    is Resource.Error-> println("Error" )
                }
            }
        })
    }

    private fun observeSliderMovie(){
        homeViewModel.getPopularMovie().observe(viewLifecycleOwner, {movies ->
            if (movies!=null){
                when(movies){
                    is Resource.Loading-> println("Loading")
                    is Resource.Success->{
                        slideAdapter.setDataSlider(movies.data?.subList(0,3))
                    }
                    is Resource.Error-> println("Error")
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}