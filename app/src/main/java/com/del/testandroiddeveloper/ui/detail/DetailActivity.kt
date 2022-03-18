package com.del.testandroiddeveloper.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.del.testandroiddeveloper.R
import com.del.testandroiddeveloper.databinding.ActivityDetailBinding
import com.del.testandroiddeveloper.domain.model.Movie
import org.koin.android.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val detailMovie = intent.getParcelableExtra<Movie>(EXTRA_MOVIE)
        if (detailMovie != null) {
            populateDetail(detailMovie)
        }

        binding.backButton.setOnClickListener { onBackPressed() }
    }

    private fun populateDetail(movie: Movie) {
        with(binding) {
            movieTitle.text = movie.title
            moveDate.text = movie.releaseDate
            movieOverview.text = movie.overview
            movieScore.text = movie.voteAverage.toString()
            Glide.with(this@DetailActivity)
                .load(getString(R.string.baseUrlImage, movie.posterPath))
                .into(imgPoster)
            imgPoster.tag = movie.posterPath


            var favoriteState = movie.favorite
            setFavoriteState(favoriteState)

            binding.favButton.setOnClickListener {
                favoriteState = !favoriteState
                println(favoriteState)
                viewModel.setFavoriteMovie(movie, favoriteState)
                setFavoriteState(favoriteState)
            }
        }
    }

    private fun setFavoriteState(state: Boolean) {
        if (state) {
            binding.favButton.text = getString(R.string.remove_fav)
        } else {
            binding.favButton.text = getString(R.string.add_fav)
        }
    }


    companion object {
        const val EXTRA_MOVIE = "extraMovie"
    }
}