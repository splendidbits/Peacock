package com.splendidbits.peacock.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.Observer
import androidx.lifecycle.OnLifecycleEvent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.splendidbits.peacock.R
import com.splendidbits.peacock.adapter.TrendingRecyclerAdapter
import com.splendidbits.peacock.dao.NewsRepository
import com.splendidbits.peacock.main.PeacockApplication
import com.splendidbits.peacock.model.Item
import kotlinx.android.synthetic.main.fragment_trending.*
import javax.inject.Inject


class TrendingFragment : Fragment() {
    private val lifecycleObserver = FragmentLifecycleObserver()
    private var recyclerView: RecyclerView? = null

    @Inject
    lateinit var repository: NewsRepository

    @Inject
    lateinit var recyclerAdapter: TrendingRecyclerAdapter

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

        recyclerView = view.findViewById<RecyclerView>(R.id.trendingRecyclerView)
        recyclerView?.layoutManager = LinearLayoutManager(context)
        recyclerView?.adapter = recyclerAdapter

        swipeRefreshLayout.isRefreshing = true
        swipeRefreshLayout.setDistanceToTriggerSync(200)
        swipeRefreshLayout.setColorSchemeResources(R.color.peacock_orange, R.color.peacock_maroon, R.color.peacock_purple)
        swipeRefreshLayout.setOnRefreshListener (object : SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                lifecycleObserver.loadTrendingItems()
            }
        })
    }

    fun scrollToTop() {
        recyclerView?.smoothScrollToPosition(0)
    }

    inner class FragmentLifecycleObserver : LifecycleObserver {
        @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
        fun loadTrendingItems() {
            repository.getLatestBatch().observe(this@TrendingFragment, Observer {

                Log.d(this.javaClass.simpleName, "Loaded ${it?.items?.size} articles")
                if (it != null) {
                    recyclerAdapter.submitList(it.items as MutableList<Item>)
                    swipeRefreshLayout.isRefreshing = false
                }
            })
        }
    }
}