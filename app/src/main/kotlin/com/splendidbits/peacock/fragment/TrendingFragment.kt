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
import com.splendidbits.peacock.R
import com.splendidbits.peacock.adapter.TrendingRecyclerAdapter
import com.splendidbits.peacock.dao.NewsRepository
import com.splendidbits.peacock.main.PeacockApplication
import com.splendidbits.peacock.model.Item
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
        val recyclerView = view.findViewById<RecyclerView>(R.id.trendingRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = recyclerAdapter
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