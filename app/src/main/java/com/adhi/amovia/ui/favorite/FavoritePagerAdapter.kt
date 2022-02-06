package com.adhi.amovia.ui.favorite

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.adhi.amovia.ui.favorite.FavoriteFragment.Companion.FAVORITE_TAB_TITLES
import com.adhi.amovia.ui.favorite.movie.FavoriteMovieFragment
import com.adhi.amovia.ui.favorite.tv.FavoriteTvFragment

class FavoritePagerAdapter(activity: AppCompatActivity) :
    FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = FAVORITE_TAB_TITLES.size

    override fun createFragment(position: Int): Fragment =
        when (position) {
            0 -> FavoriteMovieFragment()
            else -> FavoriteTvFragment()
        }
}