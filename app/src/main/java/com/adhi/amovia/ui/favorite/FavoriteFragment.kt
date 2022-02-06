package com.adhi.amovia.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.adhi.amovia.R
import com.adhi.amovia.databinding.FragmentFavoriteBinding
import com.adhi.amovia.ui.home.HomeFragment
import com.google.android.material.tabs.TabLayoutMediator

class FavoriteFragment : Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        config()
    }

    private fun config() {
        val favoritePagerAdapter = FavoritePagerAdapter(requireActivity() as AppCompatActivity)
        binding?.apply {
            viewPager.adapter = favoritePagerAdapter
            TabLayoutMediator(tabs, viewPager) { tab, position ->
                tab.text = resources.getString(HomeFragment.HOME_TAB_TITLES[position])
            }.attach()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @StringRes
        val FAVORITE_TAB_TITLES = intArrayOf(
            R.string.movie,
            R.string.tv_show
        )
    }

}