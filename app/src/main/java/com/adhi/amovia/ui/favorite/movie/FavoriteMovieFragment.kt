package com.adhi.amovia.ui.favorite.movie

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.adhi.amovia.R
import com.adhi.amovia.data.source.local.entity.DetailMovieEntity
import com.adhi.amovia.databinding.FragmentFavoriteMovieBinding
import com.adhi.amovia.ui.detail.DetailMovieActivity
import com.adhi.amovia.ui.detail.DetailMovieViewModel
import com.adhi.amovia.utils.Utility.notFound
import com.adhi.amovia.viewmodel.ViewModelFactory
import com.google.android.material.snackbar.Snackbar

class FavoriteMovieFragment : Fragment() {
    private lateinit var favMovieAdapter: FavoriteMovieAdapter
    private lateinit var viewModel: DetailMovieViewModel
    private var _binding: FragmentFavoriteMovieBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteMovieBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getFavorite().observe(viewLifecycleOwner, {
            if (it != null) {
                favMovieAdapter.submitList(it)
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            val viewModelFactory = ViewModelFactory.getInstance(requireActivity())
            viewModel =
                ViewModelProvider(this, viewModelFactory)[DetailMovieViewModel::class.java]
            favMovieAdapter = FavoriteMovieAdapter()
            viewModel.getFavorite().observe(viewLifecycleOwner, {
                if (it.isNotEmpty()) {
                    binding?.empty?.notFound(false)
                    favMovieAdapter.submitList(it)
                    favMovieAdapter.notifyDataSetChanged()
                } else binding?.empty?.notFound(true)
            })

            with(binding?.rvMovies) {
                this?.layoutManager = LinearLayoutManager(context)
                this?.setHasFixedSize(true)
                this?.adapter = favMovieAdapter
            }

            favMovieAdapter.setOnItemClickCallback(object :
                FavoriteMovieAdapter.OnFavMovieClickCallback {
                override fun onItemClicked(data: DetailMovieEntity) = showDetail(data)
            })

            favMovieAdapter.setOnDeleteClickCallback(object :
            FavoriteMovieAdapter.OnDeleteMovieClickCallback {
                override fun onDeleteClicked(movie: DetailMovieEntity) = deleteMovie(movie)
            })
        }
    }

    private fun deleteMovie(movie: DetailMovieEntity) {
        movie.let { viewModel.setFavorite(it) }
        Snackbar.make(view as View, R.string.favorite_removed, Snackbar.LENGTH_LONG)
            .apply {
                setAction(R.string.undo) {
                    movie.let { viewModel.setFavorite(it) }
                }
                setActionTextColor(Color.RED)
                show()
            }
    }

    private fun showDetail(data: DetailMovieEntity) {
        val intent = Intent(context, DetailMovieActivity::class.java)
        intent.putExtra(DetailMovieActivity.EXTRA_MOVIE, data.id)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}