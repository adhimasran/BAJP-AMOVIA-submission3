package com.adhi.amovia.ui.favorite.tv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.adhi.amovia.data.source.local.entity.DetailTvEntity
import com.adhi.amovia.databinding.ItemsListFavoriteBinding
import com.adhi.amovia.utils.Utility.loadImage

class FavoriteTvAdapter :
    PagedListAdapter<DetailTvEntity, FavoriteTvAdapter.TvViewHolder>(DIFF_CALLBACK) {
    private lateinit var onItemClickCallback: OnFavTvClickCallback

    private lateinit var onDeleteClickCallback: OnDeleteTvClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnFavTvClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setOnDeleteClickCallback(onDeleteTvClickCallback: OnDeleteTvClickCallback) {
        this.onDeleteClickCallback = onDeleteTvClickCallback
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteTvAdapter.TvViewHolder {
        val binding = ItemsListFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TvViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteTvAdapter.TvViewHolder, position: Int) {
        val tv = getItem(position)
        if (tv != null) {
            holder.bind(tv)
            holder.itemView.setOnClickListener {
                onItemClickCallback.onItemClicked(tv)
            }
            holder.binding.ibDelete.setOnClickListener {
                onDeleteClickCallback.onDeleteClicked(tv)
            }
        }
    }

    inner class TvViewHolder(val binding: ItemsListFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tv: DetailTvEntity) {
            with(binding) {
                tvTitle.text = tv.name
                tvRating.text = tv.rating.toString()
                imgPoster.loadImage(tv.imgPoster)
            }
        }
    }

    interface OnFavTvClickCallback {
        fun onItemClicked(data: DetailTvEntity)
    }

    interface OnDeleteTvClickCallback {
        fun onDeleteClicked(tv: DetailTvEntity)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DetailTvEntity>() {
            override fun areItemsTheSame(
                oldItem: DetailTvEntity,
                newItem: DetailTvEntity
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: DetailTvEntity,
                newItem: DetailTvEntity
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}