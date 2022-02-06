package com.adhi.amovia.ui.favorite.tv

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
import com.adhi.amovia.data.source.local.entity.DetailTvEntity
import com.adhi.amovia.databinding.FragmentFavoriteTvBinding
import com.adhi.amovia.ui.detail.DetailTvActivity
import com.adhi.amovia.ui.detail.DetailTvViewModel
import com.adhi.amovia.utils.Utility.notFound
import com.adhi.amovia.viewmodel.ViewModelFactory
import com.google.android.material.snackbar.Snackbar

class FavoriteTvFragment : Fragment() {
    private lateinit var favTvAdapter: FavoriteTvAdapter
    private lateinit var viewModel: DetailTvViewModel
    private var _binding: FragmentFavoriteTvBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteTvBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getFavorite().observe(viewLifecycleOwner, {
            if (it != null) {
                favTvAdapter.submitList(it)
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            val viewModelFactory = ViewModelFactory.getInstance(requireActivity())
            viewModel = ViewModelProvider(this, viewModelFactory)[DetailTvViewModel::class.java]
            favTvAdapter = FavoriteTvAdapter()
            viewModel.getFavorite().observe(viewLifecycleOwner, {
                if (it.isNotEmpty()) {
                    binding?.empty?.notFound(false)
                    favTvAdapter.submitList(it)
                    favTvAdapter.notifyDataSetChanged()
                } else binding?.empty?.notFound(true)
            })

            with(binding?.rvTvShow) {
                this?.layoutManager = LinearLayoutManager(context)
                this?.setHasFixedSize(true)
                this?.adapter = favTvAdapter
            }

            favTvAdapter.setOnItemClickCallback(object :
                FavoriteTvAdapter.OnFavTvClickCallback {
                override fun onItemClicked(data: DetailTvEntity) = showDetail(data)
            })

            favTvAdapter.setOnDeleteClickCallback(object :
                FavoriteTvAdapter.OnDeleteTvClickCallback {
                override fun onDeleteClicked(tv: DetailTvEntity) = deleteTv(tv)
            })
        }
    }

    private fun deleteTv(tv: DetailTvEntity) {
        tv.let { viewModel.setFavorite(it) }
        Snackbar.make(view as View, R.string.favorite_removed, Snackbar.LENGTH_LONG)
            .apply {
                setAction(R.string.undo) {
                    tv.let { viewModel.setFavorite(it) }
                }
                setActionTextColor(Color.RED)
                show()
            }
    }

    private fun showDetail(data: DetailTvEntity) {
        val intent = Intent(context, DetailTvActivity::class.java)
        intent.putExtra(DetailTvActivity.EXTRA_TV, data.id)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}