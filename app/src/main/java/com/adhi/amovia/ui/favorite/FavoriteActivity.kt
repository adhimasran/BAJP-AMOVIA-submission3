package com.adhi.amovia.ui.favorite

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.adhi.amovia.databinding.ActivityFavoriteBinding
import com.adhi.amovia.ui.home.HomeFragment
import com.google.android.material.tabs.TabLayoutMediator

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayShowTitleEnabled(false)

        config()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun config() {
        val favoritePagerAdapter = FavoritePagerAdapter(this)
        binding.apply {
            viewPager.adapter = favoritePagerAdapter
            TabLayoutMediator(tabs, viewPager) { tab, position ->
                tab.text = resources.getString(HomeFragment.HOME_TAB_TITLES[position])
            }.attach()
        }
    }
}