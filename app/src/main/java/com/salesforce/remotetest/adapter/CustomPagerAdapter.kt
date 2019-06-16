package com.salesforce.remotetest.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.salesforce.remotetest.favorites.FavoriteMoviesListFragment
import com.salesforce.remotetest.searchmoviestask.SearchMoviesFragment

class CustomPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                SearchMoviesFragment()
            }
            else -> {
                return FavoriteMoviesListFragment()
            }
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return null
    }
}