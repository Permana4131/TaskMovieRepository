package com.del.testandroiddeveloper.ui.popular

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.del.testandroiddeveloper.R
import com.del.testandroiddeveloper.data.Resource
import com.del.testandroiddeveloper.databinding.FragmentPopularBinding
import com.del.testandroiddeveloper.ui.adapter.MoviePopularAdapter
import com.del.testandroiddeveloper.ui.detail.DetailActivity
import org.koin.android.viewmodel.ext.android.viewModel

class PopularFragment : Fragment() {

    private var _binding: FragmentPopularBinding? = null
    private val popularViewModel: PopularViewModel by viewModel()
    private val binding get() = _binding!!
    private lateinit var popularAdapter: MoviePopularAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        popularAdapter = MoviePopularAdapter()
        _binding = FragmentPopularBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding.rvPopular){
            layoutManager = GridLayoutManager(context, 2)
            adapter = popularAdapter
        }

        observePopularMovie()

        popularAdapter.onItemClick={ selectedData ->
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_MOVIE, selectedData)
            startActivity(intent)
        }

        binding.searchView.apply {
            queryHint = resources.getString(R.string.search_hint)
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    popularViewModel.setSearch(query)
                    binding.searchView.clearFocus()
                    observeSearch()
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    popularViewModel.setSearch(newText)
                    if(newText!=""){
                        observeSearch()
                    }
                    return false
                }
            })
        }
    }

    private fun observePopularMovie(){
        popularViewModel.getPopularMovie().observe(viewLifecycleOwner, {movie->
            if (movie!=null){
                when(movie){
                    is Resource.Loading-> println("Loading")
                    is Resource.Success->{
                        popularAdapter.setData(movie.data)
                    }
                    is Resource.Error-> println("Error")
                }
            }
        })
    }

    private fun observeSearch(){
        popularViewModel.movie.observe(viewLifecycleOwner, {movie->
            popularAdapter.setData(movie)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}