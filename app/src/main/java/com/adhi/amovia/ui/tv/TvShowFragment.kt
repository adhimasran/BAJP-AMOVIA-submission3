package com.adhi.amovia.ui.tv

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
import com.adhi.amovia.data.source.local.entity.TvEntity
import com.adhi.amovia.databinding.FragmentTvShowBinding
import com.adhi.amovia.ui.detail.DetailTvActivity
import com.adhi.amovia.utils.SortUtils
import com.adhi.amovia.utils.Utility.isPortrait
import com.adhi.amovia.utils.Utility.loading
import com.adhi.amovia.viewmodel.ViewModelFactory
import com.adhi.amovia.vo.Resource
import com.adhi.amovia.vo.Status

class TvShowFragment : Fragment() {

    private lateinit var tvAdapter: TvAdapter
    private lateinit var viewModel: TvShowViewModel
    private var _binding: FragmentTvShowBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTvShowBinding.inflate(inflater, container, false)
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
                viewModel.getTvShows(sort).observe(viewLifecycleOwner, observer)
            }
        }

        viewModel.getTvShows(sort).observe(viewLifecycleOwner, observer)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            val viewModelFactory = ViewModelFactory.getInstance(requireActivity())
            viewModel = ViewModelProvider(this, viewModelFactory)[TvShowViewModel::class.java]
            tvAdapter = TvAdapter()

            binding?.rvTvShow?.apply {
                layoutManager = GridLayoutManager(context, getSpanCount())
                setHasFixedSize(true)
                adapter = tvAdapter
            }

            tvAdapter.setOnItemClickCallback(object : TvAdapter.OnTvClickCallback {
                override fun onItemClicked(data: TvEntity) = showDetail(data)
            })
        }
    }

    private val observer = Observer<Resource<PagedList<TvEntity>>> {
        if (it != null) {
            when (it.status) {
                Status.LOADING -> isLoading(true)
                Status.SUCCESS -> {
                    isLoading(false)
                    tvAdapter.submitList(it.data)
                    tvAdapter.notifyDataSetChanged()
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

    private fun showDetail(data: TvEntity) {
        val intent = Intent(context, DetailTvActivity::class.java)
        intent.putExtra(DetailTvActivity.EXTRA_TV, data.id)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}