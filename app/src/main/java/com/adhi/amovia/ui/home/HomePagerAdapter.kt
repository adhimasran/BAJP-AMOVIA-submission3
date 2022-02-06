package com.adhi.amovia.ui.home

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.adhi.amovia.ui.home.HomeFragment.Companion.HOME_TAB_TITLES
import com.adhi.amovia.ui.movie.MovieFragment
import com.adhi.amovia.ui.tv.TvShowFragment

class HomePagerAdapter(activity: AppCompatActivity) :
    FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = HOME_TAB_TITLES.size

    override fun createFragment(position: Int): Fragment =
        when (position) {
            0 -> MovieFragment()
            else -> TvShowFragment()
        }
}