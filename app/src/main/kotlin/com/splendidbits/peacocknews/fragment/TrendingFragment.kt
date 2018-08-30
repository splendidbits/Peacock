package com.splendidbits.peacocknews.fragment

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
import com.splendidbits.peacocknews.R
import com.splendidbits.peacocknews.adapter.TrendingRecyclerAdapter
import com.splendidbits.peacocknews.dao.NewsRepository
import com.splendidbits.peacocknews.helper.logTag
import com.splendidbits.peacocknews.main.PeacockApplication
import com.splendidbits.peacocknews.model.Item
import kotlinx.android.synthetic.main.fragment_trending.*
import java.util.*
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

        recyclerView = view.findViewById(R.id.trendingRecyclerView)
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
            // Observer for updated batches
            repository.getLatestBatch().observe(viewLifecycleOwner, Observer { batches ->
                Log.d(logTag(), "${batches?.batches?.size} (${batches?.dataSourceType?.name})")

                // Merge and sort the items from each batch.
                val sortedItems = fun(): List<Item> {
                    val items = mutableListOf<Item>()
                    batches?.batches?.forEach({ items.addAll(it.items) })

                    Collections.sort(items, object : Comparator<Item> {
                        override fun compare(item1: Item?, item2: Item?): Int {
                            return if (item1?.published?.after(item2?.published) ?: false) 1
                            else if (item1?.published?.before(item2?.published) ?: false) -1
                            else 0
                        }
                    })
                    return items
                }.invoke()


                if (sortedItems.isNotEmpty()) {
                    recyclerAdapter.submitList(sortedItems)
                    swipeRefreshLayout.isRefreshing = false
                }
            })
        }
    }
}