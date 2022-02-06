package com.adhi.amovia.ui.tv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.adhi.amovia.data.source.local.entity.TvEntity
import com.adhi.amovia.databinding.ItemsGridBinding
import com.adhi.amovia.utils.Utility.loadImage

class TvAdapter : PagedListAdapter<TvEntity, TvAdapter.TvViewHolder>(DIFF_CALLBACK) {
    private lateinit var onItemClickCallback: OnTvClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnTvClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvViewHolder {
        val binding = ItemsGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TvViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TvViewHolder, position: Int) {
        val tv = getItem(position)
        if (tv != null) {
            holder.bind(tv)
            holder.itemView.setOnClickListener {
                onItemClickCallback.onItemClicked(tv)
            }
        }
    }

    inner class TvViewHolder(private val binding: ItemsGridBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tv: TvEntity) {
            with(binding) {
                tvTitle.text = tv.name
                tvRating.text = tv.rating.toString()
                imgPoster.loadImage(tv.imgPoster)
            }
        }
    }

    interface OnTvClickCallback {
        fun onItemClicked(data: TvEntity)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TvEntity>() {
            override fun areItemsTheSame(oldItem: TvEntity, newItem: TvEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TvEntity, newItem: TvEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}