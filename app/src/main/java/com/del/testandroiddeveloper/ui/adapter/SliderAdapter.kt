package com.del.testandroiddeveloper.ui.adapter

import android.graphics.drawable.Drawable
import com.smarteist.autoimageslider.SliderViewAdapter
import com.bumptech.glide.Glide
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.ViewGroup
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.del.testandroiddeveloper.R
import com.del.testandroiddeveloper.databinding.SliderLayoutBinding
import com.del.testandroiddeveloper.domain.model.Movie

class SliderAdapter : SliderViewAdapter<SliderAdapter.SliderAdapterViewHolder>() {
    private var mSliderItems = ArrayList<Movie>()
    var onItemClick: ((Movie) -> Unit)? = null

    fun setDataSlider(newSliderData: List<Movie>?) {
        if (newSliderData == null) return
        mSliderItems.addAll(newSliderData)
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup): SliderAdapterViewHolder =
        SliderAdapterViewHolder(
            SliderLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )


    override fun onBindViewHolder(viewHolder: SliderAdapterViewHolder, position: Int) {
        viewHolder.bind(mSliderItems[position])
        viewHolder.position = position
    }


    override fun getCount(): Int = mSliderItems.size

    inner class SliderAdapterViewHolder(private val binding: SliderLayoutBinding) :
        SliderViewAdapter.ViewHolder(binding.root) {
        var position: Int = 0
        fun bind(sliderItem: Movie) {
            Glide.with(itemView.context)
                .load(itemView.context.getString(R.string.baseUrlImage, sliderItem.backdropPath))
                .fitCenter()
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.progressBar.visibility = GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.progressBar.visibility = GONE
                        return false
                    }
                })
                .into(binding.myimage)

            binding.title.text = sliderItem.title
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(mSliderItems[position])
            }
        }
    }
}