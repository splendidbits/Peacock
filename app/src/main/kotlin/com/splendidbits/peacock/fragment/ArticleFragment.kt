package com.splendidbits.peacock.fragment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.customtabs.CustomTabsIntent
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.splendidbits.peacock.R
import com.splendidbits.peacock.main.PeacockApplication


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
            findNavController().navigateUp()
        }
    }

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)

        if (arguments != null && arguments?.containsKey("url")!!) {
            val builder = CustomTabsIntent.Builder()
            val customTabsIntent = builder.build()
            customTabsIntent.launchUrl(getActivity(), Uri.parse(arguments?.getString("url")))
        }
    }
}