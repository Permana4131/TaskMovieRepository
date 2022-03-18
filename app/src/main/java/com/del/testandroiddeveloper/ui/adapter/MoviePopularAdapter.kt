package com.del.testandroiddeveloper.ui.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.del.testandroiddeveloper.R
import com.del.testandroiddeveloper.databinding.ListItemPopularBinding
import com.del.testandroiddeveloper.domain.model.Movie
import com.del.testandroiddeveloper.utlis.DiffUtils
import java.util.ArrayList

class MoviePopularAdapter: RecyclerView.Adapter<MoviePopularAdapter.MovieViewHolder>() {
    private var listData = ArrayList<Movie>()
    var onItemClick: ((Movie) -> Unit)? = null

    fun setData(newListData: List<Movie>?) {
        if (newListData == null) return
        val diffUtilCallback = DiffUtils(listData, newListData)
        val diffResult = DiffUtil.calculateDiff(diffUtilCallback)
        listData.clear()
        listData.addAll(newListData)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class MovieViewHolder (private val binding: ListItemPopularBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(movie: Movie){
            if (movie.posterPath=="null"){
                Glide.with(itemView.context)
                    .load("https://user-images.githubusercontent.com/24848110/33519396-7e56363c-d79d-11e7-969b-09782f5ccbab.png")
                    .fitCenter()
                    .into(binding.imgMovie)
            }else{
                Glide.with(itemView.context)
                    .load(itemView.context.getString(R.string.baseUrlImage, movie.posterPath))
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
//                        progressBar.visibility = GONE
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
//                        progressBar.visibility = GONE
                            return false
                        }
                    })
                    .into(binding.imgMovie)
            }

            binding.apply {
                movieTitle.text = movie.title
                movieOverview.text = movie.overview
            }

        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listData[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieViewHolder = MovieViewHolder(ListItemPopularBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int){
        holder.bind(listData[position])
    }

    override fun getItemCount(): Int = listData.size
}