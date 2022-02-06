package com.adhi.amovia.ui.favorite.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.adhi.amovia.data.source.local.entity.DetailMovieEntity
import com.adhi.amovia.databinding.ItemsListFavoriteBinding
import com.adhi.amovia.utils.Utility.loadImage

class FavoriteMovieAdapter :
    PagedListAdapter<DetailMovieEntity, FavoriteMovieAdapter.MovieViewHolder>(DIFF_CALLBACK) {
    private lateinit var onItemClickCallback: OnFavMovieClickCallback

    private lateinit var onDeleteClickCallback: OnDeleteMovieClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnFavMovieClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setOnDeleteClickCallback(onDeleteClickCallback: OnDeleteMovieClickCallback) {
        this.onDeleteClickCallback = onDeleteClickCallback
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteMovieAdapter.MovieViewHolder {
        val binding =
            ItemsListFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteMovieAdapter.MovieViewHolder, position: Int) {
        val movie = getItem(position)
        if (movie != null) {
            holder.bind(movie)
            holder.itemView.setOnClickListener {
                onItemClickCallback.onItemClicked(movie)
            }

            holder.binding.ibDelete.setOnClickListener {
                onDeleteClickCallback.onDeleteClicked(movie)
            }
        }
    }

    inner class MovieViewHolder(val binding: ItemsListFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: DetailMovieEntity) {
            with(binding) {
                tvTitle.text = movie.title
                tvRating.text = movie.rating.toString()
                imgPoster.loadImage(movie.imgPoster)
            }
        }
    }

    interface OnFavMovieClickCallback {
        fun onItemClicked(data: DetailMovieEntity)
    }

    interface OnDeleteMovieClickCallback {
        fun onDeleteClicked(movie: DetailMovieEntity)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DetailMovieEntity>() {
            override fun areItemsTheSame(
                oldItem: DetailMovieEntity,
                newItem: DetailMovieEntity
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: DetailMovieEntity,
                newItem: DetailMovieEntity
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}