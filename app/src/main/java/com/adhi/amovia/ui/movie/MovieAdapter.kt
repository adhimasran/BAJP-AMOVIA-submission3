package com.adhi.amovia.ui.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.adhi.amovia.data.source.local.entity.MovieEntity
import com.adhi.amovia.databinding.ItemsGridBinding
import com.adhi.amovia.utils.Utility.loadImage

class MovieAdapter : PagedListAdapter<MovieEntity, MovieAdapter.MovieViewHolder>(DIFF_CALLBACK) {
    private lateinit var onItemClickCallback: OnMovieClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnMovieClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemsGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        if (movie != null) {
            holder.bind(movie)
            holder.itemView.setOnClickListener {
                onItemClickCallback.onItemClicked(movie)
            }
        }
    }

    inner class MovieViewHolder(private val binding: ItemsGridBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieEntity) {
            with(binding) {
                tvTitle.text = movie.title
                tvRating.text = movie.rating.toString()
                imgPoster.loadImage(movie.imgPoster)
            }
        }
    }

    interface OnMovieClickCallback {
        fun onItemClicked(data: MovieEntity)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieEntity>() {
            override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}