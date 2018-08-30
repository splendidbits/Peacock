package com.splendidbits.peacocknews.activity

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.splendidbits.peacocknews.R
import com.splendidbits.peacocknews.fragment.TrendingFragment
import com.splendidbits.peacocknews.main.PeacockApplication
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class NewsActivity : AppCompatActivity() {
    @Inject
    lateinit var context: Context

    init {
        PeacockApplication.graph.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        PeacockApplication.graph.inject(this)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        bottomNavigation.selectedItemId = R.id.navigationTrending
        bottomNavigation.setOnNavigationItemSelectedListener({ item: MenuItem ->
            when {
                item.itemId == R.id.navigationTrending -> {
                    val currentFragment = navHost.childFragmentManager.fragments[0]
                    if (currentFragment is TrendingFragment){
                        currentFragment.scrollToTop()
                    }
                }
                item.itemId == R.id.navigationExplore -> {
                }
                item.itemId == R.id.navigationSaved -> {
                }
            }
            true
        })
    }
}
