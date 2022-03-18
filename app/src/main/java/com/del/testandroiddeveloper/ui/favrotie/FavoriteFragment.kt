package com.del.testandroiddeveloper.ui.favrotie

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.del.testandroiddeveloper.R
import com.del.testandroiddeveloper.databinding.FragmentFavoriteBinding
import com.del.testandroiddeveloper.ui.adapter.FavoriteAdapter
import com.del.testandroiddeveloper.ui.detail.DetailActivity
import org.koin.android.viewmodel.ext.android.viewModel


class FavoriteFragment : Fragment() {

    private val favoriteViewModel: FavoriteViewModel by viewModel()
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private lateinit var favAadapter: FavoriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        favAadapter = FavoriteAdapter()
        activity?.actionBar?.hide()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding.rvFavorite) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = favAadapter
        }

        observeDataFavorite()

        favAadapter.onItemClick = { selectedData ->
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_MOVIE, selectedData)
            startActivity(intent)
        }

        binding.searchView.apply {
            queryHint = resources.getString(R.string.search_hint)
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    favoriteViewModel.setSearch(query)
                    binding.searchView.clearFocus()
                    observeFavoriteMovie()
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    favoriteViewModel.setSearch(newText)
                    if(newText!=""){
                        observeFavoriteMovie()
                    }
                    return false
                }
            })
        }
    }

    private fun observeDataFavorite() {
        favoriteViewModel.getFavoriteMovie().observe(viewLifecycleOwner, { movies ->
            if (movies != null) {
                favAadapter.setData(movies)
            }
        })
    }

    private fun observeFavoriteMovie() {
        favoriteViewModel.movie.observe(viewLifecycleOwner, { movie ->
            favAadapter.setData(movie)
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}