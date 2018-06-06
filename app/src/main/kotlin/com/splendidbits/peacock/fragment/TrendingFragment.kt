package com.splendidbits.peacock.fragment

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.content.Context
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
import com.splendidbits.peacock.model.TrendingBatch
import com.splendidbits.peacock.model.TrendingBatchNewsItems
import com.splendidbits.peacock.model.NewsItem
import com.splendidbits.peacock.service.NewsDatabase
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_trending.*
import javax.inject.Inject


class TrendingFragment : Fragment() {
    @Inject
    lateinit var newsDatabase: NewsDatabase

    @Inject
    lateinit var repository: NewsRepository

    @Inject
    lateinit var recyclerAdapter: TrendingRecyclerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        PeacockApplication.graph.inject(this)
        return LayoutInflater.from(context).inflate(R.layout.fragment_trending, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        PeacockApplication.graph.inject(this)

        trendingRecyclerView.layoutManager = LinearLayoutManager(context)
        trendingRecyclerView.adapter = recyclerAdapter

        val liveData: LiveData<TrendingBatch> = repository.getLatestApiTrendingItems()
        val savedData: LiveData<TrendingBatchNewsItems> = newsDatabase.trendingRoomDao().getLatestSavedTrendingItems()

        // We want to observe bother the database cache, the API data, and also add an observer for
        // deleting stale data and re-caching.
        liveData.observe(this, liveTrendingDataObserver)
        savedData.observe(this, savedTrendingDataObserver)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        activity?.title = getString(R.string.fragment_trending)
    }

    private val savedTrendingDataObserver = Observer<TrendingBatchNewsItems> { trendingGroup ->
        Log.d(this.javaClass.simpleName, "Loaded ${trendingGroup?.newsItems?.size} cached articles")
        if (trendingGroup?.newsItems != null && trendingRecyclerView.childCount == 0) {
            recyclerAdapter.submitList(trendingGroup.newsItems as MutableList<NewsItem>)
        }
    }

    private val liveTrendingDataObserver  = Observer<TrendingBatch> { trendingGroup ->
        if (trendingGroup != null) {
            Observable.just(newsDatabase)
                    .subscribeOn(Schedulers.io())
                    .subscribe {
                        it.trendingRoomDao().insertTrendingGroup(trendingGroup)
                        it.trendingRoomDao().insertTrendingItems(trendingGroup.newsItems)
                    }
        }
        recyclerAdapter.submitList((trendingGroup?.newsItems as MutableList<NewsItem>?)!!)
    }
}