package com.splendidbits.peacock.fragment

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.Observer
import android.arch.lifecycle.OnLifecycleEvent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.splendidbits.peacock.R
import com.splendidbits.peacock.adapter.TrendingRecyclerAdapter
import com.splendidbits.peacock.dao.NewsRepository
import com.splendidbits.peacock.main.PeacockApplication
import com.splendidbits.peacock.model.Item
import kotlinx.android.synthetic.main.fragment_trending.*
import javax.inject.Inject


class TrendingFragment : Fragment() {
    @Inject
    lateinit var repository: NewsRepository

    @Inject
    lateinit var recyclerAdapter: TrendingRecyclerAdapter

    private val lifecycleObserver = FragmentLifecycleObserver()

    override fun onStart() {
        lifecycle.addObserver(lifecycleObserver)
        super.onStart()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        PeacockApplication.graph.inject(this)
        return LayoutInflater.from(context).inflate(R.layout.fragment_trending, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        trendingRecyclerView.layoutManager = LinearLayoutManager(context)
        trendingRecyclerView.adapter = recyclerAdapter
    }

    inner class FragmentLifecycleObserver : LifecycleObserver {
        @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
        fun loadTrendingItems() {
            repository.getLatestBatch().observe(this@TrendingFragment, Observer {

                Log.d(this.javaClass.simpleName, "Loaded ${it?.items?.size} articles")
                if (it?.items != null && !it.items.isEmpty()) {
                    recyclerAdapter.submitList(it.items as MutableList<Item>)
                }
            })
        }
    }
}