package com.adhi.amovia.ui.detail

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.adhi.amovia.R
import com.adhi.amovia.data.source.local.entity.DetailTvEntity
import com.adhi.amovia.databinding.ActivityDetailTvBinding
import com.adhi.amovia.ui.favorite.FavoriteActivity
import com.adhi.amovia.utils.Utility.addChip
import com.adhi.amovia.utils.Utility.convertToDate
import com.adhi.amovia.utils.Utility.convertToTime
import com.adhi.amovia.utils.Utility.isFavorite
import com.adhi.amovia.utils.Utility.loadImage
import com.adhi.amovia.viewmodel.ViewModelFactory
import com.adhi.amovia.vo.Status
import com.google.android.material.snackbar.Snackbar

class DetailTvActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailTvBinding
    private val viewModelFactory = ViewModelFactory.getInstance(this)
    private val viewModel: DetailTvViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailTvBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayShowTitleEnabled(false)
        actionBar?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val id = intent.getIntExtra(EXTRA_TV, 0)
        viewModel.getDetailTv(id).observe(this, {
            if (it != null) {
                when (it.status) {
                    Status.LOADING -> setShimmer(true)
                    Status.SUCCESS -> {
                        setShimmer(false)
                        if (it.data != null) {
                            setDetail(it.data)
                        }
                    }
                    Status.ERROR -> {
                        setShimmer(false)
                        Toast.makeText(this, "There's an error", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.navigation_favorite -> {
                startActivity(Intent(this, FavoriteActivity::class.java))
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun setDetail(tv: DetailTvEntity) {
        val year = tv.firstAir.convertToDate().split(", ")[1]
        val genre: List<String> = tv.genres.split(",").map { it.trim() }

        binding.apply {
            tvTitle.text = resources.getString(R.string.title, tv.name, year)
            tvTagline.text = tv.tagline
            tvRating.text = tv.rating.toString()
            tvRatingCount.text =
                resources.getString(R.string.rating_count, tv.ratingCount.toString())
            tvRelease.text = tv.firstAir.convertToDate()
            tvDuration.text = resources.getString(R.string.duration, tv.runtime.convertToTime())
            tvOverview.text = tv.overview
            imgPoster.loadImage(tv.imgPoster)
            imgBackdrop.loadImage(tv.imgBackdrop)
            with(chipGenre) {
                removeAllViews()
                genre.map { addChip(this@DetailTvActivity, it) }
            }
            btnFavorite.apply {
                isFavorite(tv.favorite)
                setOnClickListener {
                    viewModel.setFavorite(tv)
                    Snackbar.make(
                        this,
                        R.string.favorite_added,
                        Snackbar.LENGTH_LONG
                    ).apply {
                        setAction(R.string.see_favorite) {
                            startActivity(
                                Intent(
                                    this@DetailTvActivity,
                                    FavoriteActivity::class.java
                                )
                            )
                        }
                        setActionTextColor(resources.getColor(R.color.gold))
                        show()
                    }
                    if (!tv.favorite) {
                        Snackbar.make(
                            this,
                            R.string.favorite_removed,
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }

    private fun setShimmer(state: Boolean) {
        if (state) {
            binding.shimmer.visibility = View.VISIBLE
            binding.tvRating.visibility = View.GONE
            binding.tvTitleOverview.visibility = View.GONE
            binding.tvTitleGenre.visibility = View.GONE
            binding.btnFavorite.visibility = View.GONE
        } else {
            binding.shimmer.visibility = View.GONE
            binding.tvRating.visibility = View.VISIBLE
            binding.tvTitleOverview.visibility = View.VISIBLE
            binding.tvTitleGenre.visibility = View.VISIBLE
            binding.btnFavorite.visibility = View.VISIBLE
        }
    }

    companion object {
        const val EXTRA_TV = "extra_tv"
    }
}