package com.salesforce.remotetest

import android.os.Bundle
import android.view.MenuItem
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.salesforce.remotetest.adapter.CustomPagerAdapter
import com.salesforce.remotetest.base.BaseActivity

class MainActivity : BaseActivity() {
    private lateinit var viewPager: ViewPager
    private lateinit var tabs: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        setupActionBar(R.id.toolbar) {}
        setupViewPager()
        setupTabIcons()
    }

    private fun setupTabIcons() {
        tabs = (findViewById<TabLayout>(R.id.tabs)).apply {
            setupWithViewPager(viewPager)
            getTabAt(0)?.setIcon(R.drawable.ic_search)
            getTabAt(1)?.setIcon(R.drawable.ic_favorite_movie)
        }
    }

    private fun setupViewPager() {
        viewPager = (findViewById<ViewPager>(R.id.viewpager))
            .apply {
            }
        val fragmentAdapter = CustomPagerAdapter(supportFragmentManager)
        viewPager.adapter = fragmentAdapter
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            android.R.id.home -> {

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
}