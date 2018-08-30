package com.splendidbits.peacocknews.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.splendidbits.peacocknews.R
import com.splendidbits.peacocknews.main.PeacockApplication


class ArticleFragment : Fragment() {
    private var visitedArticle = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        PeacockApplication.graph.inject(this)
        return LayoutInflater.from(context).inflate(R.layout.fragment_article, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val url = arguments?.getString("externalId")
        Log.d(ArticleFragment::class.java.simpleName, "Found externalId $url")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (visitedArticle) {
//            findNavController().navigateUp()
        }
    }

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
    }
}