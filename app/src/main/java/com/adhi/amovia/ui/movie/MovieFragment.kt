package com.adhi.amovia.ui.movie

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import com.adhi.amovia.data.source.local.entity.MovieEntity
import com.adhi.amovia.databinding.FragmentMovieBinding
import com.adhi.amovia.ui.detail.DetailMovieActivity
import com.adhi.amovia.utils.SortUtils
import com.adhi.amovia.utils.Utility.isPortrait
import com.adhi.amovia.utils.Utility.loading
import com.adhi.amovia.viewmodel.ViewModelFactory
import com.adhi.amovia.vo.Resource
import com.adhi.amovia.vo.Status

class MovieFragment : Fragment() {
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var viewModel: MovieViewModel
    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onResume() {
        super.onResume()
        val items = arrayListOf("Default", "Best Rating")
        var sort = ""
        binding?.apply {
            spinner.setItems(items)
            spinner.setOnItemSelectedListener { _, _, _, filter ->
                when (filter) {
                    "Default" -> sort = SortUtils.DEFAULT
                    "Best Rating" -> sort = SortUtils.RATING
                }
                viewModel.getMovies(sort).observe(viewLifecycleOwner, observer)
            }
        }

        viewModel.getMovies(sort).observe(viewLifecycleOwner, observer)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            val viewModelFactory = ViewModelFactory.getInstance(requireActivity())
            viewModel = ViewModelProvider(this, viewModelFactory)[MovieViewModel::class.java]
            movieAdapter = MovieAdapter()

            binding?.rvMovies?.apply {
                layoutManager = GridLayoutManager(context, getSpanCount())
                setHasFixedSize(true)
                adapter = movieAdapter
            }

            movieAdapter.setOnItemClickCallback(object : MovieAdapter.OnMovieClickCallback {
                override fun onItemClicked(data: MovieEntity) = showDetail(data)
            })
        }
    }

    private val observer = Observer<Resource<PagedList<MovieEntity>>> {
        if (it != null) {
            when (it.status) {
                Status.LOADING -> isLoading(true)
                Status.SUCCESS -> {
                    isLoading(false)
                    movieAdapter.submitList(it.data)
                    movieAdapter.notifyDataSetChanged()
                }
                Status.ERROR -> {
                    isLoading(false)
                    Toast.makeText(context, "There's an error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun getSpanCount() = if (isPortrait()) 2 else 4

    private fun isLoading(state: Boolean) = binding?.progressBar?.loading(state)

    private fun showDetail(data: MovieEntity) {
        val intent = Intent(context, DetailMovieActivity::class.java)
        intent.putExtra(DetailMovieActivity.EXTRA_MOVIE, data.id)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}