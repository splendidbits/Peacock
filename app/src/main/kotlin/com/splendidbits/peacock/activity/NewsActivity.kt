package com.splendidbits.peacock.activity

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.splendidbits.peacock.R
import com.splendidbits.peacock.main.PeacockApplication
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
